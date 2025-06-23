import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AppService } from '../services/app.service';
import { Router } from '@angular/router';
import { AddGameConfigRequest } from '../model/add-game-config-request.model';

@Component({
  selector: 'app-config',
  templateUrl: './config.component.html',
  styleUrls: ['./config.component.css']
})
export class ConfigComponent implements OnDestroy {
  model: AddGameConfigRequest;
  private addGameConfigSubscription?: Subscription;

  constructor (private appService: AppService, private router: Router) {
    this.model = {xCoord: undefined, yCoord: undefined, animal: ""};
  } 
  
  onFormSubmit() {
    if (!this.model) return;
    const url = document.getElementById("photoUrl")?.textContent;
    this.addGameConfigSubscription = this.appService.addGameConfig(this.model).subscribe(
      {
        next : () => {
          this.navigateToLogin();
        }
      }
    )
  }

  navigateToLogin() {
    this.router.navigateByUrl('/login');
  }

  ngOnDestroy(): void {
    this.addGameConfigSubscription?.unsubscribe();
  }
}
