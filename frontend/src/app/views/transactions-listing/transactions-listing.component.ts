import {Component} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {RouterLink} from "@angular/router";
import {SeperatorComponent} from "../../components/seperator/seperator.component";
import {Pageable} from "../../models/utils/Pagination";
import {MaterialModule} from "../../modules/shared/material/material.module";
import {MatTableDataSource} from "@angular/material/table";
import {TransactionService} from "../../services/transaction/transaction.service";
import {Transaction} from "../../models/entities/Transaction";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-transactions-listing',
  standalone: true,
  imports: [
    MaterialModule,
    RouterLink,
    SeperatorComponent,
    DatePipe
  ],
  templateUrl: './transactions-listing.component.html',
  styleUrl: './transactions-listing.component.css'
})
export class TransactionsListingComponent {
  title = 'Latest Transactions';

  displayColumns: string[] = ['id', 'customer', 'account', 'type', 'balance', 'amount', 'success', 'message', 'created'];
  transactionsTableData: MatTableDataSource<Transaction> =
    new MatTableDataSource();
  emptyTableMessage = 'No Transactions found';

  currentPage = 0;
  pageSize = 10;
  totalItems = 0;

  constructor(
    private transactionService: TransactionService
  ) {
  }

  ngOnInit(): void {
    this.fetchTransactionsTableData();
  }

  fetchTransactionsTableData() {
    const paginationParams: Pageable = {
      page: this.currentPage,
      size: this.pageSize,
    };

    this.transactionService.getAll(paginationParams).subscribe((res) => {
      this.transactionsTableData.data = res.data;
      this.totalItems = res.pagination?.totalElements || 0;
    });
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.fetchTransactionsTableData();
  }
}
