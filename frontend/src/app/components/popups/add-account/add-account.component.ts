import { Component, Inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MaterialModule } from '../../../modules/shared/material/material.module';
import { AccountService } from '../../../services/account/account.service';
import { Account } from '../../../models/entities/Account';
import { ToastrService } from 'ngx-toastr';
import {SeperatorComponent} from "../../seperator/seperator.component";

@Component({
  selector: 'app-add-account',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, SeperatorComponent],
  templateUrl: './add-account.component.html',
  styleUrl: './add-account.component.css',
})
export class AddAccountComponent {
  title = 'Add Account';

  constructor(
    private builder: FormBuilder,
    private ref: MatDialogRef<AddAccountComponent>,
    private accountService: AccountService,
    @Inject(MAT_DIALOG_DATA) public customerId: number = 0,
    private toastr: ToastrService
  ) {}

  closePopup(newAccountAdded: boolean = false) {
    this.ref.close(newAccountAdded);
  }

  form = this.builder.group({
    balance: this.builder.control(0, [
      Validators.required,
      Validators.min(0),
      Validators.pattern('^[0-9]+(.[0-9]{1,2})?$'),
    ]),
  });

  submit() {
    if (this.form.valid) {
      const formData: Account = {
        balance: this.form.value.balance as number,
        customer: this.customerId as number,
      };

      this.accountService.createAccount(formData).subscribe({
        next: () => {
          this.toastr.success('Account created successfully', 'Success', {
            timeOut: 2000,
          });
          this.closePopup(true);
        },
        error: () => {
          this.toastr.error('Failed to create account', 'Error', {
            timeOut: 2000,
          });
          this.closePopup();
        },
      });
    }
  }
}
