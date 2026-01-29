import { Component } from '@angular/core';
import { IonApp, IonRouterOutlet } from '@ionic/angular/standalone';
import { IonicModule } from '@ionic/angular'
import { provideRouter, Router, RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http'
import { bootstrapApplication } from '@angular/platform-browser';
import { AuthGuard } from './guards/auth-guard';
import { AuthInterceptor } from './interceptors/auth-interceptor';

import { LoginComponent } from './features/login/login/login.component';
import { CarListComponent } from './features/car/car-list/car-list/car-list.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  standalone: true,
  imports: [IonApp, IonRouterOutlet, FormsModule, HttpClientModule, RouterModule],
})
export class AppComponent {
  constructor() {}
}
