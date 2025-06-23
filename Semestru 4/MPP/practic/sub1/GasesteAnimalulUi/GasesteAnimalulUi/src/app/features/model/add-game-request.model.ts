import { GameConfig } from "./game-config.model";

export interface AddGameRequest {
    nickname: string,
    gameConfigurationId: number,
    won: boolean, 
    attempts: string
}