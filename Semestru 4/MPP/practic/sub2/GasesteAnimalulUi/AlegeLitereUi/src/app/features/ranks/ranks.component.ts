import { Component, OnDestroy, OnInit } from '@angular/core';
import { Game } from '../model/game.model';
import { Observable, Subscription } from 'rxjs';
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
  games$?: Observable<Game[]>;
  
  constructor (private appService: AppService, private http: HttpClient) {
  }
  
  ngOnInit(): void {
    this.games$ = this.appService.getWonGames();
  }
  
  getPositionAnimal(attempts: string) {
    return attempts.split(",").pop();
  }
  
  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }
}
