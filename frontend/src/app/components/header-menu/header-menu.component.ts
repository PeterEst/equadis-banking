import {Component} from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {
  IsActiveMatchOptions,
  Route,
  Router,
  RouterLink,
} from '@angular/router';

@Component({
  selector: 'app-header-menu',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, RouterLink],
  templateUrl: './header-menu.component.html',
  styleUrl: './header-menu.component.css',
})
export class HeaderMenuComponent {
  routes: Route[] = [];

  constructor(private router: Router) {
    this.routes = this.router.config.filter(
      (route) => route.data && route.data['displayInHeader']
    );
  }

  isActiveRoute(route: string) {
    const matchOptions: IsActiveMatchOptions = {
      paths: 'exact',
      queryParams: 'subset',
      fragment: 'ignored',
      matrixParams: 'ignored',
    };

    return this.router.isActive(route, matchOptions);
  }

  buttonTheme(route: string | undefined) {
    if (route === undefined) return '';

    return this.isActiveRoute(route) ? 'accent' : '';
  }

  handleMenuTitleClick() {
    this.router.navigate(['/']);
  }
}
