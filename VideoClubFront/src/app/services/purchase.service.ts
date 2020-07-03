import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { ActionDto } from '../dto/action.dto';
import { Action } from '../model/action.model';
import { MessageDto } from '../dto/message.dto';
import { Purchase } from '../model/purchase.model';

@Injectable({providedIn: 'root'})
export class PurchaseService {

  constructor(private http: HttpClient) {
    
  }

    createPurchase = (offerId: string): Observable<Purchase> => {
      return this.http.get<Purchase>(`/api/purchase/create/${offerId}`).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    getPurchase = (id:string): Observable<Purchase> => {
    return this.http.get<Purchase>(`/api/purchase/${id}`).pipe(
      map( (res: any) => {
          return res;
      })  );
    }

    getPurchasesByUser = (): Observable<Purchase[]> => {
        return this.http.get<Purchase[]>(`/api/purchases`).pipe(
          map( (res: any) => {
              return res;
          })  );
        }
}