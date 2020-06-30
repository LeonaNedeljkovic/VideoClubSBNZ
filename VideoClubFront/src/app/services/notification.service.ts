import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { MessageDto } from '../dto/message.dto';
import { Notification } from '../model/notification.model';

@Injectable({providedIn: 'root'})
export class NotificationService {

  constructor(private http: HttpClient) {
    
  }

    openNotification = (id: string): Observable<Notification> => {
      return this.http.get<Notification>(`/api/notification/open/${id}`).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    getNotifications = (): Observable<Notification[]> => {
    return this.http.get<Notification[]>(`/api/notifications`).pipe(
      map( (res: any) => {
          return res;
      })  );
    }
}