import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { ActionDto } from '../dto/action.dto';
import { Action } from '../model/action.model';
import { MessageDto } from '../dto/message.dto';
import { Offer } from '../model/offer.model';
import { OfferDto } from '../dto/offer-dto';

@Injectable({providedIn: 'root'})
export class OfferService {

  constructor(private http: HttpClient) {
    
  }

    createOffer = (offer: Offer): Observable<Offer> => {
      return this.http.post<Offer>("/api/offer", offer).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    updateOffer = (id: string, offer: Offer): Observable<Offer> => {
        return this.http.put<Offer>(`/api/offer/${id}`, offer).pipe(
          map( (res: any) => {
              return res;
          })  );
      }

    getOffer = (id:string): Observable<Offer> => {
    return this.http.get<Action>(`/api/offer/${id}`).pipe(
      map( (res: any) => {
          return res;
      })  );
    }

    getOffers = (): Observable<Offer[]> => {
        return this.http.get<Offer[]>("/api/offers").pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getOffersForUser = (): Observable<OfferDto[]> => {
      return this.http.get<Offer[]>("/api/offers/actions").pipe(
        map( (res: any) => {
            return res;
        })  );
    }
    
    deleteOffer = (id:string): Observable<MessageDto> => {
        return this.http.delete<MessageDto>(`/api/offer/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }
}