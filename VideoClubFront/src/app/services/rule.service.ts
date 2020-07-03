import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { ActionDto } from '../dto/action.dto';
import { Action } from '../model/action.model';
import { MessageDto } from '../dto/message.dto';
import { AgeClassifier } from '../model/age-classifier.model';
import { User } from '../model/user.model';
import { GenreAgeRestrictionTemplate } from '../model/genre-age-restriction-tempale.model';
import { Film } from '../model/film.model';
import { FilmAgeRestrictionTemplate } from '../model/film-age-restriction-template.model';
import { FreeMinutes } from '../model/free-minutes.model';
import { PointsDto } from '../dto/points.dto';
import { Report } from '../model/report.model';

@Injectable({providedIn: 'root'})
export class RuleService {

  constructor(private http: HttpClient) {
    
  }

    classifyUserByAge= (ageClassifier: AgeClassifier[]): Observable<User[]> => {
      return this.http.put<User[]>("/api/classify_user/age", ageClassifier).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    restrictGenreByAge = (genreAgeRestriction :GenreAgeRestrictionTemplate[]): Observable<Film[]> => {
    return this.http.put<Film[]>(`/api/restrict_genre/age`, genreAgeRestriction).pipe(
      map( (res: any) => {
          return res;
      })  );
    }

    restrictFilmByAge = (filmAgeRestriction :FilmAgeRestrictionTemplate[]): Observable<Film[]> => {
        return this.http.put<Film[]>(`/api/restrict_film/age`, filmAgeRestriction).pipe(
          map( (res: any) => {
              return res;
          })  );
        }
    
    freeMinutesByAge = (freeMinutes :FreeMinutes[]): Observable<User[]> => {
        return this.http.put<User[]>(`/api/free_minutes/age`, freeMinutes).pipe(
            map( (res: any) => {
                return res;
            })  );
        }

    freeMinutesByTitle = (freeMinutes :FreeMinutes[]): Observable<User[]> => {
        return this.http.put<User[]>(`/api/free_minutes/title`, freeMinutes).pipe(
            map( (res: any) => {
                return res;
            })  );
        }

    freeMinutesByAgeAndTitle = (freeMinutes :FreeMinutes[]): Observable<User[]> => {
        return this.http.put<User[]>(`/api/free_minutes/age/title`, freeMinutes).pipe(
            map( (res: any) => {
                return res;
            })  );
        }

    getBronzeImmunity = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/bronze_immunity_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getSilverImmunity = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/silver_immunity_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getGoldImmunity = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/gold_immunity_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getBronzeTitleAquire = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/bronze_title/aquire_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getSilverTitleAquire = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/silver_title/aquire_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getGoldTitleAquire = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/gold_title/aquire_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getBronzeTitleSave = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/bronze_title/save_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getSilverTitleSave = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/silver_title/save_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getGoldTitleSave = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/gold_title/save_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getBronzeTitleReward = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/bronze_title/reward_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getSilverTitleReward = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/silver_title/reward_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getGoldTitleReward = (): Observable<PointsDto> => {
        return this.http.get<PointsDto>("/api/gold_title/reward_points").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getAllReports = (): Observable<Report[]> => {
        return this.http.get<PointsDto>("/api/reports/all").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setBronzeImmunityPoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/bronze_immunity_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setSilverImmunityPoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/silver_immunity_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setGoldImmunityPoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/gold_immunity_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setBronzeAquirePoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/bronze_title/aquire_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setSilverAquirePoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/silver_title/aquire_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setGoldAquirePoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/gold_title/aquire_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setBronzeSavePoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/bronze_title/save_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setSilverSavePoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/silver_title/save_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setGoldSavePoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/gold_title/save_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setBronzeRewardPoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/bronze_title/reward_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setSilverRewardPoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/silver_title/reward_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    setGoldRewardPoints = (points: PointsDto): Observable<PointsDto> => {
        return this.http.put<PointsDto>("/api/gold_title/reward_points",points).pipe(
          map( (res: any) => {
              return res;
          })  );
    }


}