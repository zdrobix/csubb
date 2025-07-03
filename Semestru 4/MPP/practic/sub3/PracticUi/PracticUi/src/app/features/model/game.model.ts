import { Timestamp } from "rxjs";
import { GameConfig } from "./game-config.model";

export interface Game {
    gameConfigAnimals: string,
    nickname: string,
    points: number,
    guessed: number,
    duration: number
}