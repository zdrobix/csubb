import { Component, OnDestroy } from '@angular/core';
import { AppService } from '../services/app.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../model/login-request.model';
import { Subscription } from 'rxjs';
import { __asyncDelegator } from 'tslib';
import { User } from '../model/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnDestroy {

  model: LoginRequest;
  private loginSubscription?: Subscription;
  
  constructor (private appService: AppService, private router: Router) {
    this.model = {nickname: ""};
  } 
  
  onFormSubmit() {
    if (!this.model) return;
    this.loginSubscription = this.appService.getUserByNickname(this.model).subscribe(
      {
        next: (user: User) => {
          localStorage.setItem('loggedUser', user.nickname)
          this.router.navigateByUrl('/game');
        },
        error: (error: any) => {
          alert("Invalid nickname, or something.")
        }
      }
    )
  }

  navigateToConfig() {
    this.router.navigateByUrl('/config');
  }

  ngOnDestroy(): void {
    this.loginSubscription?.unsubscribe();
  }
}
