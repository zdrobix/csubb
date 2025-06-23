import { GameConfig } from "./game-config.model";

export interface AddGameRequest {
    nickname: string,
    gameConfigurationId: number,
    won: boolean, 
    attempts: string,
    points: number,
    startedAt: Date
}