import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AccountService } from '../../../services/account/account.service';
import { AddTransactionComponent } from '../../../components/popups/add-transaction/add-transaction.component';
import { MatTableDataSource } from '@angular/material/table';
import { Transaction } from '../../../models/entities/Transaction';
import { DatePipe } from '@angular/common';
import { MaterialModule } from '../../../modules/shared/material/material.module';
import { Account } from '../../../models/entities/Account';
import { Customer } from '../../../models/entities/Customer';
import { BackButtonComponent } from '../../../components/back-button/back-button.component';
import { PageEvent } from '@angular/material/paginator';
import {SeperatorComponent} from "../../../components/seperator/seperator.component";

@Component({
  selector: 'app-account-transactions',
  standalone: true,
  imports: [MaterialModule, DatePipe, RouterLink, BackButtonComponent, SeperatorComponent],
  templateUrl: './account-transactions.component.html',
  styleUrl: './account-transactions.component.css',
})
export class AccountTransactionsComponent {
  private readonly accountId = this.route.snapshot.params['id'];

  accountDetails: Account | null = null;
  title = `Transactions`;

  transactionsTableData: MatTableDataSource<Transaction> =
    new MatTableDataSource<Transaction>();
  emptyTableMessage = 'No transactions found for this account';
  displayColumns: string[] = [
    'id',
    'type',
    'balance',
    'amount',
    'success',
    'message',
    'created',
  ];

  currentPage = 0;
  pageSize = 10;
  totalItems = 0;

  constructor(
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private accountService: AccountService,
    private navigationRouter: Router
  ) {}

  ngOnInit(): void {
    this.fetchTransactionsTableData();
    this.fetchAccountDetails();
  }

  private fetchTransactionsTableData(): void {
    const options = {
      page: this.currentPage,
      size: this.pageSize,
    };

    this.accountService
      .getAccountTransactions(this.accountId, options)
      .subscribe({
        next: (res) => {
          this.transactionsTableData.data = res.data;
          this.totalItems = res.pagination?.totalElements || 0;
        },
        error: (error) => {
          if (error.status === 404) {
            this.navigationRouter.navigate(['/customers']);
          }
        },
      });
  }

  private fetchAccountDetails(): void {
    this.accountService.getAccount(this.accountId).subscribe({
      next: (res) => {
        this.accountDetails = res.data;
        this.extractCustomerDetails();
      },
      error: (error) => {
        if (error.status === 404) {
          this.navigationRouter.navigate(['/customers']);
        }
      },
    });
  }

  // Type guard for Customer
  isCustomer(obj: any): obj is Customer {
    return obj && typeof obj === 'object' && 'id' in obj && 'name' in obj;
  }

  extractCustomerDetails() {
    const customer = this.accountDetails?.customer;

    if (this.isCustomer(customer)) {
      return {
        link: `/customers/${customer.id}`,
        name: customer.name,
      };
    }
    return {
      link: `/customers/${customer}`,
      name: `Customer ${customer}`,
    };
  }

  openTransactionPopup() {
    const dialog = this.dialog.open(AddTransactionComponent, {
      width: '50%',
      data: this.accountId,
    });

    dialog.afterClosed().subscribe((newTransactionAdded) => {
      if (newTransactionAdded) {
        this.fetchTransactionsTableData();
        this.fetchAccountDetails();
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.fetchTransactionsTableData();
  }
}
