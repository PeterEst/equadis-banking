import { Customer } from './Customer';

export interface Account {
  id?: string;
  balance: number;
  customer: Customer | number;
}
