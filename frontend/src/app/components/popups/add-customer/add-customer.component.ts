import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MaterialModule } from '../../../modules/shared/material/material.module';
import { Customer } from '../../../models/entities/Customer';
import { CustomerService } from '../../../services/customer/customer.service';
import { ToastrService } from 'ngx-toastr';
import {SeperatorComponent} from "../../seperator/seperator.component";

@Component({
  selector: 'app-add-customer',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, SeperatorComponent],
  templateUrl: './add-customer.component.html',
  styleUrl: './add-customer.component.css',
})
export class AddCustomerComponent {
  title = 'Add Customer';

  constructor(
    private builder: FormBuilder,
    private ref: MatDialogRef<AddCustomerComponent>,
    private customerService: CustomerService,
    private toastr: ToastrService
  ) {}

  closePopup(newCustomerAdded: boolean = false) {
    this.ref.close(newCustomerAdded);
  }

  form = this.builder.group({
    name: this.builder.control('', Validators.required),
  });

  submit() {
    if (this.form.valid) {
      const formData: Customer = {
        name: this.form.value.name as string,
      };

      this.customerService.addCustomer(formData).subscribe({
        next: () => {
          this.toastr.success('Customer added successfully', 'Success', {
            timeOut: 2000,
          });
          this.closePopup(true);
        },
        error: () => {
          this.toastr.error('Failed to add customer', 'Error', {
            timeOut: 2000,
          });
          this.closePopup();
        },
      });
    }
  }
}
