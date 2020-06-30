import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { MessageDto } from '../dto/message.dto';
import { ActionEventDto } from '../dto/action-event.dto';
import { ActionEvent } from '../model/action-event.model';

@Injectable({providedIn: 'root'})
export class ActionEventService {

  constructor(private http: HttpClient) {
    
  }

    createActionEvent = (data: ActionEventDto): Observable<ActionEvent> => {
      return this.http.post<ActionEvent>("/api/action_event", data).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    updateActionEvent = (data: ActionEventDto): Observable<ActionEvent> => {
        return this.http.put<ActionEvent>("/api/action_event", data).pipe(
          map( (res: any) => {
              return res;
          })  );
      }

    getActionEvent = (id:string): Observable<ActionEvent> => {
    return this.http.get<ActionEvent>(`/api/action_event/${id}`).pipe(
      map( (res: any) => {
          return res;
      })  );
    }

    getActionEventByAction = (id:string): Observable<ActionEvent> => {
        return this.http.get<ActionEvent>(`/api/action_event/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getActionEvents = (): Observable<ActionEvent[]> => {
        return this.http.get<ActionEvent[]>("/api/action_events").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    deleteActionEvent = (id: string): Observable<MessageDto> => {
        return this.http.delete<MessageDto>(`/api/action_event/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }
}