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
          let message2 = res && res['message'];
          if(message2){
            console.log(res['message']);
            console.log(res['result']);
            localStorage.setItem('messagePurchase', JSON.stringify({
              message: res['message'],
              result: res['result']
            }));
          }
          return res;
        }) );
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