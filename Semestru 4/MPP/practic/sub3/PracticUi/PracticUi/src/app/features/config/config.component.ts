import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { AppService } from '../services/app.service';
import { Router } from '@angular/router';
import { AddGameConfigRequest } from '../model/add-game-config-request.model';
import { GameConfig } from '../model/game-config.model';

@Component({
  selector: 'app-config',
  templateUrl: './config.component.html',
  styleUrls: ['./config.component.css']
})
export class ConfigComponent implements OnInit, OnDestroy {
  configuration?: GameConfig;
  model?: AddGameConfigRequest;
  private addGameConfigSubscription?: Subscription;
  private getGameConfigsSubscription? : Subscription;

  constructor (private appService: AppService, private router: Router) {
  } 
  ngOnInit(): void {
    this.getGameConfigsSubscription = this.appService.getGameConfig().subscribe({
      next : (config: GameConfig) => {
        this.configuration = config;
        console.log(config);
        this.model = {animals: config.animals};
      }
    })
  }
  
  onFormSubmit() {
    this.addGameConfigSubscription = this.appService.updateGameConfig(this.model!, this.configuration?.id!).subscribe({
      next : () => {
        console.log("update done");
        this.navigateToLogin();
      }
    })
  }

  navigateToLogin() {
    this.router.navigateByUrl('/login');
  }

  ngOnDestroy(): void {
    this.addGameConfigSubscription?.unsubscribe();
    this.getGameConfigsSubscription?.unsubscribe();
  }
}
