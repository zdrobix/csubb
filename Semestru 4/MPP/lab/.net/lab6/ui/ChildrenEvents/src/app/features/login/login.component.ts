import { Component, OnDestroy } from '@angular/core';
import { LoginRequest } from '../models/login-request.model';
import { NotFoundError, Subscription } from 'rxjs';
import { AppServiceService } from '../services/app-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnDestroy {
  model: LoginRequest;
  loginSubscription?: Subscription;

  constructor(private service: AppServiceService, private router: Router) {
    this.model = {
      username: '',
      password: ''
    }
  }

  onFormSubmit() {
    if (!this.model)
      return;
    this.loginSubscription = this.service.login(this.model).subscribe({
      next: () => {
        this.router.navigateByUrl('/main');
      },
      error: (error: any) => {
        console.log(error);
      }
    }
    )
  }

  ngOnDestroy(): void {
    this.loginSubscription?.unsubscribe();
  }

}
