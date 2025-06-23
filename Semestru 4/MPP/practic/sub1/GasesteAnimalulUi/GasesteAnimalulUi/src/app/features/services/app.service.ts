import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../model/login-request.model';
import { User } from '../model/user.model';
import { Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { GameConfig } from '../model/game-config.model';
import { AddGameRequest } from '../model/add-game-request.model';
import { Game } from '../model/game.model';
import { AddGameConfigRequest } from '../model/add-game-config-request.model';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  nickname: string = "";

  constructor(private http: HttpClient) { }

  getUserByNickname (request: LoginRequest) : Observable<User> {
    this.nickname = request.nickname;
    return this.http.post<User>(`${environment.apiBaseUrl}/api/user/login`, request);
  }

  getGameConfig () : Observable<GameConfig> {
    return this.http.get<GameConfig>(`${environment.apiBaseUrl}/api/gameconfig`)
  }

  addGameConfig(request: AddGameConfigRequest) {
    return this.http.post<GameConfig>(`${environment.apiBaseUrl}/api/gameconfig`, request)
  }

  addGame(request: AddGameRequest) : Observable<Game>{
    return this.http.post<Game>(`${environment.apiBaseUrl}/api/game`, request);
  }

  getWonGames() : Observable<Game[]> {
    return this.http.get<Game[]>(`${environment.apiBaseUrl}/api/game/won`);
  }

  getGameConfiguration(id: number) : Observable<GameConfig> {
    return this.http.get<GameConfig>(`${environment.apiBaseUrl}/api/gameconfig/${id}`)
  }
}
