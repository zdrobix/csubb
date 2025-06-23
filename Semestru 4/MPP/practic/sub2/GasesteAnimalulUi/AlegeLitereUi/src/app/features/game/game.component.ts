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

  player: Attempt[] = [];
  server: Attempt[] = [];

  choice: Attempt[] = [];

  letters?: string[];
  points?: number[];

  startDate: Date = new Date();

  configuration?: GameConfig;
  private getGameConfigSubscription?: Subscription;
  private addGameSubscription?: Subscription;

  constructor(private appService: AppService, private router: Router) { }

  ngOnInit(): void {
    this.getGameConfigSubscription = this.appService.getGameConfig().subscribe(
      {
        next: (gameConfig: GameConfig) => {
          this.configuration = { id: gameConfig.id, letters: gameConfig.letters, points: gameConfig.points }
          this.letters = gameConfig.letters.split(',');
          this.points = gameConfig.points.split(',').map(x => parseInt(x));
          for (let i = 0; i <= 3; i++) {
            this.choice.push({
              letter: gameConfig.letters.split(',').at(i)!,
              point: parseInt(gameConfig.points.split(',').at(i)!)
            });
          }
        }
      }
    )
  }

  onCellClick(i: number, button: HTMLButtonElement) {
    button.disabled = true;

    const playerLetter = this.letters?.at(i)!;
    const playerPoint = this.points?.at(i)!;
    this.createButton(playerLetter, playerPoint, "lightgreen");
    this.player.push({ letter: playerLetter, point: playerPoint });

    let randomIndex = Math.floor(Math.random() * this.choice.length);
    let att = this.choice.at(randomIndex)!
    this.choice.splice(randomIndex, 1);

    this.createButton(att.letter, att.point, "pink")
    this.server.push(att);

    if (playerPoint > att.point) 
    {
      this.pointCount += (playerPoint + att.point)
    } else if (playerPoint < att.point) {
      this.pointCount -= playerPoint;
    }

    const pointHeader = document.getElementById("pointHeader");
    if (pointHeader) {
      pointHeader.textContent = "Points: " + this.pointCount;
    }
    console.log(this.choice.length)

    if (this.choice.length == 0) {
        const attemptsString = this.player
          .map((p, idx) => {
            const s = this.server[idx];
            if (s) {
              return `${p.letter}:${p.point},${s.letter}:${s.point}`;
            } else {
              return `${p.letter}:${p.point}`;
            }
          })
          .join(',');

        const request : AddGameRequest = {
            nickname: this.appService.nickname,
            gameConfigurationId: this.configuration?.id!,
            won: this.pointCount > 0,
            attempts: attemptsString,
            points: this.pointCount,
            startedAt: this.startDate
          };
        this.addGameSubscription = this.appService.addGame(request).subscribe(
          {
            next : () => {}
          }
        )
    }
  }

  createButton(letter: string, point: number, color: string) {
    let elem = document.createElement('button');
    elem.textContent = letter + ":" + point;
    elem.style.setProperty("background-color", color)
    elem.style.setProperty("width", "35px");
    document.getElementById("letterChoice")?.appendChild(
      elem
    )
  }

  navigateToRanks() {
    this.router.navigateByUrl('/ranks');
  }

  ngOnDestroy(): void {
    this.getGameConfigSubscription?.unsubscribe();
    this.addGameSubscription?.unsubscribe();
  }

}
