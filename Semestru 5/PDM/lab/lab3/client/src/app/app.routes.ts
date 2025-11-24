import { Routes } from '@angular/router';
import { ViewListComponent } from './features/view-list/view-list.component';
import { AddElemComponent } from './features/add-elem/add-elem.component';
import { LocateElemComponent } from './features/locate-elem/locate-elem.component';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./home/home.page').then((m) => m.HomePage),
  },
  {
    path: '',
    redirectTo: 'list',
    pathMatch: 'full',
  },
  {
    path: 'list',
    component: ViewListComponent
  },
  {
    path: 'add',
    component: AddElemComponent
  },
  {
    path: 'locate',
    component: LocateElemComponent
  }
];
