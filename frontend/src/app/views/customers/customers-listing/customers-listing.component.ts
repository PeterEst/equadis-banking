import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {Customer} from '../../../models/entities/Customer';
import {CustomerService} from '../../../services/customer/customer.service';
import {RouterLink} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {AddCustomerComponent} from '../../../components/popups/add-customer/add-customer.component';
import {MaterialModule} from '../../../modules/shared/material/material.module';
import {Pageable} from '../../../models/utils/Pagination';
import {PageEvent} from '@angular/material/paginator';
import {SeperatorComponent} from "../../../components/seperator/seperator.component";
import {debounceTime, distinctUntilChanged, Subject, switchMap} from "rxjs";
import {
  DebouncedSearchInputComponent
} from "../../../components/debounced-search-input/debounced-search-input.component";

@Component({
  selector: 'app-customers-listing',
  standalone: true,
  imports: [MaterialModule, RouterLink, SeperatorComponent, DebouncedSearchInputComponent],
  templateUrl: './customers-listing.component.html',
  styleUrl: './customers-listing.component.css',
})
export class CustomersListingComponent implements OnInit {
  title = 'Customers';

  displayColumns: string[] = ['id', 'name', 'actions'];
  customersTableData: MatTableDataSource<Customer> =
    new MatTableDataSource<Customer>();
  emptyTableMessage = 'No customers found';

  currentPage = 0;
  pageSize = 10;
  totalItems = 0;

  constructor(
    private dialog: MatDialog,
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    this.fetchCustomersTableData();
  }

  onSearchInputChange(value: string) {
    this.fetchCustomersTableData(value);
  }

  fetchCustomersTableData(queryString: string | null = null) {
    const paginationParams: Pageable = {
      page: this.currentPage,
      size: this.pageSize,
    };

    const options = {
      ...paginationParams,
      queryString: queryString || ""
    }

    this.customerService.getAll(options).subscribe((res) => {
      this.customersTableData.data = res.data;
      this.totalItems = res.pagination?.totalElements || 0;
    });
  }

  openAddCustomerPopup() {
    const dialog = this.dialog.open(AddCustomerComponent, {
      width: '50%',
    });

    dialog.afterClosed().subscribe((newCustomerAdded) => {
      if (newCustomerAdded) {
        this.fetchCustomersTableData();
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.fetchCustomersTableData();
  }
}
