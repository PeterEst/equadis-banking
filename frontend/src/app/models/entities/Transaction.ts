import { Account } from './Account';

export interface Transaction {
  id?: number;
  amount: number;
  account: Account | number;
  type: 'DEPOSIT' | 'WITHDRAW';
  success?: boolean;
  message?: string;
  created?: Date;
}
