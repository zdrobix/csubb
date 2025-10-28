import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model';
import { LoginRequest } from 'src/app/models/login-request.model';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/services/user/user-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent  implements OnInit {

  model: LoginRequest;
  private loginSubscription?: Subscription;

  constructor(private userService: UserService, private router: Router) { 
    this.model = {
      username: '',
      password: ''
    };
  }

  ngOnInit() {}

  onFormSubmit() {
    if (!this.model)
      return;
    this.loginSubscription = this.userService.login(this.model).subscribe({
      next: () => {
        this.router.navigate(['/account']);
      },
      error: (error: any) => { console.log('Login error:', error); }
      });

  }

}
