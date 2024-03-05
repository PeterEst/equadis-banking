import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';
import {Transaction} from '../../models/entities/Transaction';
import {HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  constructor(private apiService: ApiService) {
  }

  addTransaction(transaction: Transaction) {
    return this.apiService.post<Transaction>('transactions', transaction);
  }

  getAll(options = {}) {
    const params = new HttpParams({
      fromObject: options as any,
    });

    return this.apiService.get<Transaction[]>('transactions', params);
  }
}
