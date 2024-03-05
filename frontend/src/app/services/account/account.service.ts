import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { Account } from '../../models/entities/Account';
import { Transaction } from '../../models/entities/Transaction';
import { Pageable } from '../../models/utils/Pagination';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private apiService: ApiService) {}

  getAccount(accountId: number) {
    return this.apiService.get<Account>(`accounts/${accountId}`);
  }

  createAccount(account: Account) {
    return this.apiService.post<Account>('accounts', account);
  }

  getAccountTransactions(accountId: number, options: Pageable | {} = {}) {
    const params = new HttpParams({
      fromObject: options as any,
    });

    return this.apiService.get<Transaction[]>(
      `accounts/${accountId}/transactions`,
      params
    );
  }
}
