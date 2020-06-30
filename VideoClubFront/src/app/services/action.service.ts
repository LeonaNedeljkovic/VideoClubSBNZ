import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { ActionDto } from '../dto/action.dto';
import { Action } from '../model/action.model';
import { MessageDto } from '../dto/message.dto';

@Injectable({providedIn: 'root'})
export class ActionService {

  constructor(private http: HttpClient) {
    
  }

    updateAction = (data: ActionDto): Observable<Action> => {
      return this.http.put<Action>("/api/action", data).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    getAction = (id:string): Observable<Action> => {
    return this.http.get<Action>(`/api/action/${id}`).pipe(
      map( (res: any) => {
          return res;
      })  );
    }

    getActions = (): Observable<Action[]> => {
        return this.http.get<Action[]>("/api/actions").pipe(
          map( (res: any) => {
              return res;
          })  );
    }
    getActionsByActionEvent = (id:string): Observable<Action[]> => {
        return this.http.get<Action[]>(`/api/actions/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    deleteAction = (id:string): Observable<MessageDto> => {
        return this.http.delete<MessageDto>(`/api/action/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }
}