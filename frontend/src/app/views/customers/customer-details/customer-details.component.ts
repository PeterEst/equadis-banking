import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CustomerService } from '../../../services/customer/customer.service';
import { Customer } from '../../../models/entities/Customer';
import { MaterialModule } from '../../../modules/shared/material/material.module';
import { Account } from '../../../models/entities/Account';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { AddAccountComponent } from '../../../components/popups/add-account/add-account.component';
import { BackButtonComponent } from '../../../components/back-button/back-button.component';
import { DatePipe } from '@angular/common';
import { PageEvent } from '@angular/material/paginator';
import { ToastrService } from 'ngx-toastr';
import {SeperatorComponent} from "../../../components/seperator/seperator.component";

@Component({
  selector: 'app-customer-details',
  standalone: true,
  imports: [MaterialModule, RouterLink, BackButtonComponent, DatePipe, SeperatorComponent],
  templateUrl: './customer-details.component.html',
  styleUrl: './customer-details.component.css',
})
export class CustomerDetailsComponent implements OnInit {
  private readonly customerId = this.route.snapshot.params['id'];

  title = `Customer Details`;
  customer: Customer | null = null;

  customerAccountsTableData: MatTableDataSource<Account> =
    new MatTableDataSource<Account>();
  displayColumns: string[] = ['id', 'balance', 'created', 'actions'];
  emptyTableMessage = 'No accounts found for this customer';

  currentPage = 0;
  pageSize = 10;
  totalItems = 0;

  constructor(
    private route: ActivatedRoute,
    private navigationRouter: Router,
    private customerService: CustomerService,
    private dialog: MatDialog,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchCustomerDetails();
    this.fetchCustomerAccountsTableData();
  }

  private fetchCustomerDetails(): void {
    this.customerService.getCustomer(this.customerId).subscribe({
      next: (res) => {
        this.customer = res.data;
      },
      error: (error) => {
        if (error.status === 404) {
          this.navigationRouter.navigate(['/customers']);
        }

        this.toastr.error(
          'An error occurred while fetching customer details',
          'Error',
          {
            timeOut: 2000,
          }
        );
      },
    });
  }

  private fetchCustomerAccountsTableData(): void {
    const options = {
      page: this.currentPage,
      size: this.pageSize,
    };

    this.customerService
      .getCustomerAccounts(this.customerId, options)
      .subscribe((res) => {
        this.customerAccountsTableData.data = res.data;
        this.totalItems = res.pagination?.totalElements || 0;
      });
  }

  openAddAccountPopup() {
    const dialog = this.dialog.open(AddAccountComponent, {
      width: '50%',
      data: this.customerId,
    });

    dialog.afterClosed().subscribe((newAccountAdded) => {
      if (newAccountAdded) {
        this.fetchCustomerAccountsTableData();
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.fetchCustomerAccountsTableData();
  }
}
