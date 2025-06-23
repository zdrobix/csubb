import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '../services/app.service';
import { GameConfig } from '../model/game-config.model';
import { Subscription } from 'rxjs';
import { Attempt } from '../model/attempt.model';
import { Game } from '../model/game.model';
import { AddGameRequest } from '../model/add-game-request.model';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, OnDestroy {
  matrix: number[][] = [];
  attempts: Attempt[] = [];

  private configuration?: GameConfig;
  private getGameConfigSubscription?: Subscription;
  private addGameSubscription?: Subscription;

  constructor(private appService: AppService, private router: Router) { }

  ngOnInit(): void {
    this.getGameConfigSubscription = this.appService.getGameConfig().subscribe(
      {
        next: (gameConfig: GameConfig) => {
          this.configuration = { id: gameConfig.id, animal: gameConfig.animal, xCoord: gameConfig.xCoord, yCoord: gameConfig.yCoord }
          this.matrix = Array.from({ length: 4 }, () => Array(3).fill(0));
          this.matrix[this.configuration!.xCoord][this.configuration!.yCoord] = 1;
        }
      }
    )
  }

  onCellClick(col: number, row: number, button: HTMLButtonElement) {
    if (this.attempts.length >= 3 || !this.appService.nickname) {
      return;
    }

    button.style.backgroundColor = "brown";
    this.attempts.push({ xCoord: row, yCoord: col });

    let attemptString = this.attempts.map(a => `${a.xCoord}:${a.yCoord}`).join(',');
    let gameRequest: AddGameRequest = {
      gameConfigurationId: this.configuration!.id,
      nickname: this.appService.nickname,
      attempts: attemptString,
      won: false
    };

    if (col == this.configuration?.xCoord && row == this.configuration?.yCoord) {
      gameRequest.won = true;
      this.revealImage(button);
    } else  {
      button.textContent = this.getHint(row, col, this.configuration!.xCoord, this.configuration!.yCoord);
      if (this.attempts.length != 3)
        return;
    };



    console.log(gameRequest);
    this.addGameSubscription = this.appService.addGame(gameRequest).subscribe(
      {
        next: () => {
          var header = document.getElementById("infoHeader");
          if (header) {
            header.textContent = ""
            header.textContent += "Number of tries: " + (gameRequest.won == true ? "" + gameRequest.attempts.split(",").length : "-1");
            header.textContent += " animal: " + this.configuration?.animal;
          }
        }
      }
    )
  }

  getHint(xGuess: number, yGuess: number, xActual: number, yActual: number) {
    let hint = "";

    if (xGuess < xActual) hint = "South";
    else if (xGuess > xActual) hint = "North";
    hint += "-";
    if (xGuess == xActual) hint = "";

    if (yGuess < yActual) hint += "West";
    if (yGuess > yActual) hint += "Est";

    return hint;
  }

  revealImage(button: HTMLButtonElement) {
    button.style.backgroundImage = `url("assets/${this.configuration!.animal}.png")`;
    button.style.backgroundSize = "cover";
    button.style.backgroundPosition = "center";
    button.style.backgroundRepeat = "no-repeat";
  }

  navigateToRanks() {
    this.router.navigateByUrl('/ranks');
  }

  ngOnDestroy(): void {
    this.getGameConfigSubscription?.unsubscribe();
    this.addGameSubscription?.unsubscribe();
  }

}
