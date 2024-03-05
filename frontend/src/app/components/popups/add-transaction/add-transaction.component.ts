import {Component, Inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {MaterialModule} from '../../../modules/shared/material/material.module';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Transaction} from '../../../models/entities/Transaction';
import {ToastrService} from 'ngx-toastr';
import {SeperatorComponent} from "../../seperator/seperator.component";
import {TransactionService} from "../../../services/transaction/transaction.service";

@Component({
  selector: 'app-add-transaction',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, SeperatorComponent],
  templateUrl: './add-transaction.component.html',
  styleUrl: './add-transaction.component.css',
})
export class AddTransactionComponent {
  title = 'Add Transaction';

  transactionTypes: String[] = ['DEPOSIT', 'WITHDRAW'];

  constructor(
    private builder: FormBuilder,
    private ref: MatDialogRef<AddTransactionComponent>,
    @Inject(MAT_DIALOG_DATA) public accountId: number = 0,
    private transactionService: TransactionService,
    private toastr: ToastrService
  ) {
  }

  closePopup(newTransactionAdded: boolean = false) {
    this.ref.close(newTransactionAdded);
  }

  form = this.builder.group({
    type: this.builder.control('DEPOSIT', [
      Validators.required,
      Validators.pattern('DEPOSIT|WITHDRAW'),
    ]),
    amount: this.builder.control(1, [
      Validators.required,
      Validators.min(0),
      Validators.pattern('^[0-9]+(.[0-9]{1,2})?$'),
    ]),
  });

  submit() {
    if (this.form.valid) {
      const formData: Transaction = {
        account: this.accountId as number,
        type: this.form.value.type as 'DEPOSIT' | 'WITHDRAW',
        amount: this.form.value.amount as number,
      };

      this.transactionService.addTransaction(formData).subscribe({
        next: () => {
          this.toastr.success('Transaction added successfully', 'Success', {
            timeOut: 2000,
          });
          this.closePopup(true);
        },
        error: () => {
          this.toastr.error('Failed to add transaction', 'Error', {
            timeOut: 2000,
          });
          this.closePopup();
        },
      });
    }
  }
}
