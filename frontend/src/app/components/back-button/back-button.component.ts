import { Component } from '@angular/core';
import { MaterialModule } from '../../modules/shared/material/material.module';

@Component({
  selector: 'app-back-button',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './back-button.component.html',
  styleUrl: './back-button.component.css',
})
export class BackButtonComponent {
  goBack() {
    window.history.back();
  }
}
