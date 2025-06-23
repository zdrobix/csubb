import { GameConfig } from "./game-config.model";

export interface Game {
    gameConfigurationId: number,
    nickname: string,
    attempts: string,
    won: boolean
}