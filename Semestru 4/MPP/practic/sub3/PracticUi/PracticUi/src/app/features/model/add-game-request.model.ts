import { GameConfig } from "./game-config.model";

export interface AddGameRequest {
    nickname: string,
    gameConfigAnimals: string,
    points: number,
    guessed: number,
    duration: number
}