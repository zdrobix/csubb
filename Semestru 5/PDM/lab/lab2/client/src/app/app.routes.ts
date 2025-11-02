import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login/login.component';
import { HomePage } from './home/home.page';
import { AuthGuard } from './guards/auth-guard';
import { CarListComponent } from './features/car/car-list/car-list/car-list.component';
import { CarSearchComponent } from './features/car/car-search/car-search/car-search.component';
import { LogoutComponent } from './features/logout/logout/logout.component';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./home/home.page').then((m) => m.HomePage),
  },
  {
    path: '',
    redirectTo: 'cars',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'cars',
    component: CarListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'cars/search',
    component: CarSearchComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'account',
    component: LogoutComponent,
    canActivate: [AuthGuard]
  }
];
