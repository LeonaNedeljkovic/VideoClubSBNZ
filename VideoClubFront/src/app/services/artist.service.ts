import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { MessageDto } from '../dto/message.dto';
import { Artist } from '../model/artist.model';

@Injectable({providedIn: 'root'})
export class ArtistService {

  constructor(private http: HttpClient) {
    
  }

    createArtist = (data: Artist): Observable<Artist> => {
      return this.http.post<Artist>("/api/artist", data).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    updateArtist = (data: Artist): Observable<Artist> => {
        return this.http.put<Artist>("/api/artist", data).pipe(
          map( (res: any) => {
              return res;
          })  );
      }

    getArtist = (id:string): Observable<Artist> => {
    return this.http.get<Artist>(`/api/artist/${id}`).pipe(
      map( (res: any) => {
          return res;
      })  );
    }

    getArtists = (): Observable<Artist[]> => {
        return this.http.get<Artist[]>("/api/artists").pipe(
          map( (res: any) => {
              return res;
          })  );
    }
    getActorsOfVideoContent = (videoId:string): Observable<Artist[]> => {
        return this.http.get<Artist[]>(`/api/artists/actors/${videoId}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getDirectorOfVideoContent = (videoId:string): Observable<Artist> => {
        return this.http.get<Artist[]>(`/api/artists/director/${videoId}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    deleteArtist = (id:string): Observable<MessageDto> => {
        return this.http.delete<MessageDto>(`/api/artist/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }
}