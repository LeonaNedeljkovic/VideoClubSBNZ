import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { MessageDto } from '../dto/message.dto';
import { Film } from '../model/film.model';
import { FilmDto } from '../dto/film.dto';
import { RecommendedFilm } from '../model/recommended-film.model';
import { FinalReport } from '../dto/final-report';

@Injectable({providedIn: 'root'})
export class FilmService {

  constructor(private http: HttpClient) {
    
  }

    createFilm = (data: FilmDto): Observable<MessageDto> => {
        return this.http.post<MessageDto>("/api/film", data).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    updateFilm = (data: FilmDto): Observable<Film> => {
        return this.http.put<Film>("/api/film", data).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    rateFilm = (id: string, rate: number): Observable<Film> => {
        return this.http.get<Film>(`/api/film/rate/${id}/${rate}`).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    addToFavourites = (id: string): Observable<Film[]> => {
      return this.http.get<Film[]>(`/api/film/favourites/${id}`).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    checkIfFavourite = (id: string): Observable<boolean> => {
        return this.http.get<boolean>(`/api/film/favourites/exists/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
      }

    filmsRecommended = (num: number): Observable<RecommendedFilm[]> => {
    return this.http.get<RecommendedFilm[]>(`/api/films/recommended/${num}`).pipe(
      map( (res: any) => {
          return res;
      })  );
    }

    filmReport = (film: FilmDto): Observable<FinalReport> => {
    return this.http.post<FinalReport>(`/api/film/recommended/info`, film).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    getTopRated = (num: number): Observable<Film[]> => {
        return this.http.get<Film[]>(`/api/films/top_rated/${num}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }
    getMostPopular = (num: number): Observable<Film[]> => {
        return this.http.get<Film[]>(`/api/films/most_popular/${num}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getByGenre = (genre: string): Observable<Film[]> => {
        return this.http.get<Film[]>(`/api/films/search/genre/${genre}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getByName = (name: string): Observable<Film[]> => {
        return this.http.get<Film[]>(`/api/films/search/name/${name}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getFilmInfo = (id: number): Observable<Film> => {
        return this.http.get<Film>(`/api/film/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getFilms = (): Observable<Film[]> => {
        return this.http.get<Film[]>(`/api/films`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }
}