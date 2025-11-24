import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LoginRequest } from 'src/app/models/login-request.model';
import { Service } from 'src/app/services/user/service';
import { Service as CarService } from 'src/app/services/car/service';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  styleUrls: ['./login.component.scss'],
  imports: [IonicModule, FormsModule]
})
export class LoginComponent  implements OnDestroy {

  model: LoginRequest;
  private loginSubscription?: Subscription;

  constructor(private carService: CarService, private userService: Service, private router: Router) { 
    this.model = {
      username: '',
      password: ''
    };
  }

  onFormSubmit() {
    this.loginSubscription = this.userService.login(this.model.username, this.model.password).subscribe({
      next: (response) => {
        console.log('User logged in successfully. user_id: ' + response.user_id);

        this.userService.saveToken(response.token);
        this.userService.saveUserId(response.user_id);
        this.router.navigate(['/cars']);
      },
      error: (err) => {
        console.log(err);
      }
    })
  }


  ngOnDestroy(): void {
    this.loginSubscription?.unsubscribe();
  }


}


