import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '../services/app.service';
import { GameConfig } from '../model/game-config.model';
import { Subscription, Timestamp } from 'rxjs';
import { Attempt } from '../model/attempt.model';
import { Game } from '../model/game.model';
import { AddGameRequest } from '../model/add-game-request.model';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, OnDestroy {
  pointCount: number = 0;

  matrix: number[][] = [];

  revealed: HTMLButtonElement[] = [];
  lock: boolean = false;

  animals: string[] = [];

  attemptCount: number = 0;
  revealedCount: number = 0;
  startDate: Date = new Date();

  configuration?: GameConfig;
  private getGameConfigSubscription?: Subscription;
  private addGameSubscription?: Subscription;

  constructor(private appService: AppService, private router: Router) { }

  ngOnInit(): void {
    this.getGameConfigSubscription = this.appService.getGameConfig().subscribe(
      {
        next: (gameConfig: GameConfig) => {
          this.configuration = { id: gameConfig.id, animals: gameConfig.animals}
          this.animals = gameConfig.animals.split(",");
          this.matrix = Array.from({ length: 4 }, () => Array(2).fill(0));
          
        }
        });
  }

  onCellClick(row: number, col: number, button: HTMLButtonElement) {
    if (this.lock || button.disabled || this.revealed.includes(button)) {
      return;
    }

    button.textContent = this.animals[row * 2 + col];
    this.revealed.push(button);

    if (this.revealed.length === 2) {
      this.lock = true;

      var [btn1, btn2] = this.revealed;

      if (btn1.textContent === btn2.textContent) {
        this.revealedCount ++;
        this.pointCount += 2;
        btn1.disabled = true;
        btn2.disabled = true;
        this.revealed = [];
        this.lock = false;
      } else {
        setTimeout(() => {
          btn1.textContent = "";
          btn2.textContent = "";
          this.revealed = [];
          this.lock = false;
        }, 1000);
        this.pointCount -= 1;
      }

      const pointHeader = document.getElementById("pointHeader");
      if (pointHeader) {
        pointHeader.textContent = "Points: " + this.pointCount;
      }
      
      this.attemptCount ++;
      if (this.attemptCount > 5 || this.revealedCount == 4) {
        this.addGameSubscription = this.appService.addGame(
          {
            nickname: this.appService.nickname,
            gameConfigAnimals: this.animals.join(","),
            points: this.pointCount,
            guessed: this.revealedCount,
            duration: (new Date().getTime() - this.startDate.getTime())
          }
        ).subscribe(
          {
            next : () => {
              console.log("added game");
            }
          }
        )

        
      }
    }

  }

  navigateToRanks() {
    this.router.navigateByUrl('/ranks');
  }

  ngOnDestroy(): void {
    this.getGameConfigSubscription?.unsubscribe();
    this.addGameSubscription?.unsubscribe();
  }
}
