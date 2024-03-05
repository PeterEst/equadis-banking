import {Routes} from '@angular/router';
import {CustomersListingComponent} from './views/customers/customers-listing/customers-listing.component';
import {CustomerDetailsComponent} from './views/customers/customer-details/customer-details.component';
import {AccountTransactionsComponent} from './views/accounts/account-transactions/account-transactions.component';
import {TransactionsListingComponent} from "./views/transactions-listing/transactions-listing.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/customers',
    pathMatch: 'full',
  },
  {
    path: 'customers',
    component: CustomersListingComponent,
    title: 'Customers - Equadis',
    data: {displayInHeader: true, displayName: 'Customers'},
  },
  {
    path: 'transactions',
    component: TransactionsListingComponent,
    title: 'Transactions - Equadis',
    data: {displayInHeader: true, displayName: 'Transactions'},
  },
  {
    path: 'customers/:id',
    component: CustomerDetailsComponent,
    title: 'Customer Details - Equadis',
  },
  {
    path: 'accounts/:id/transactions',
    component: AccountTransactionsComponent,
    title: 'Account Transactions - Equadis',
  },
  {
    path: '**',
    redirectTo: '/customers',
  },
];
