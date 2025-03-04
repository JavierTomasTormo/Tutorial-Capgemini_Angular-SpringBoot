import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_ROUTES } from '../../constants/api.routes';
import { Game } from '../../models/game/game.model';



@Injectable({
    providedIn: 'root'
})
export class GameService {
    constructor(private http: HttpClient) { }

    getAll(): Observable<Game[]> {
        return this.http.get<Game[]>(API_ROUTES.GAMES.GET_ALL);
    }

    getOne(id: number): Observable<Game> {
        return this.http.get<Game>(API_ROUTES.GAMES.GET_ONE(id));
    }

    create(game: Game): Observable<Game> {
        return this.http.post<Game>(API_ROUTES.GAMES.CREATE, game);
    }

    update(game: Game): Observable<Game> {
        return this.http.put<Game>(API_ROUTES.GAMES.UPDATE(game.id!), game);
    }

    delete(id: number): Observable<any> {
        return this.http.delete(API_ROUTES.GAMES.DELETE(id));
    }
}