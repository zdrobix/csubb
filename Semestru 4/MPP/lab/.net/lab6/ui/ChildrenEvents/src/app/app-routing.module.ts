import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { MainViewComponent } from './features/main-view/main-view.component';
import { EditEventComponent } from './features/edit-event/edit-event.component';
import { EditChildComponent } from './features/edit-child/edit-child.component';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'main', component: MainViewComponent, canActivate: [AuthGuard] },
  { path: 'main/event/edit/:id', component: EditEventComponent, canActivate: [AuthGuard] },
  { path: 'main/child/edit/:id', component: EditChildComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
