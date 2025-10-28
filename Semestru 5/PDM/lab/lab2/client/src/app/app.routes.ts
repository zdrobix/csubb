import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login/login.component';
import { HomePage } from './home/home.page';
import { AuthGuard } from './guards/auth-guard';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./home/home.page').then((m) => m.HomePage),
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'cars',
    component: HomePage,
    canActivate: [AuthGuard]
  }
];
