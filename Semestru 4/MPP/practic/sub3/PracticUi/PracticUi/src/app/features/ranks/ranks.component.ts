import { Component, OnDestroy, OnInit } from '@angular/core';
import { Game } from '../model/game.model';
import { interval, Observable, Subscription, switchMap } from 'rxjs';
import { AppService } from '../services/app.service';
import { HttpClient } from '@angular/common/http';
import { GameConfig } from '../model/game-config.model';

@Component({
  selector: 'app-ranks',
  templateUrl: './ranks.component.html',
  styleUrls: ['./ranks.component.css']
})
export class RanksComponent implements OnInit, OnDestroy
{
  games?: Game[];
  private intervalSubscription?: Subscription;

  constructor(private appService: AppService) {}

  ngOnInit(): void {
    this.intervalSubscription = interval(1000) 
      .pipe(
        switchMap(() => this.appService.getWonGames())
      )
      .subscribe((games: Game[]) => {
        this.games = games; 
      });
  }

  ngOnDestroy(): void {
    if (this.intervalSubscription) {
      this.intervalSubscription.unsubscribe();
    }
  }
}
