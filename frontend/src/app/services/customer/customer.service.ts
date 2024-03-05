import { Injectable } from '@angular/core';
import { Customer } from '../../models/entities/Customer';
import { ApiService } from '../api/api.service';
import { Account } from '../../models/entities/Account';
import { Pageable } from '../../models/utils/Pagination';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  constructor(private apiClient: ApiService) {}

  getAll(options= {}) {
    const params = new HttpParams({
      fromObject: options as any,
    });

    return this.apiClient.get<Customer[]>(`customers`, params);
  }

  addCustomer(customer: Customer) {
    return this.apiClient.post<Customer>(`customers`, customer);
  }

  getCustomer(id: string) {
    return this.apiClient.get<Customer>(`customers/${id}`);
  }

  getCustomerAccounts(id: string, options: Pageable | {} = {}) {
    const params = new HttpParams({
      fromObject: options as any,
    });

    return this.apiClient.get<Account[]>(`customers/${id}/accounts`, params);
  }
}
