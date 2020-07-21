import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { Artist } from '../model/artist.model';
import { Offer } from '../model/offer.model';
import { FinalReport } from '../dto/final-report';

@Injectable({providedIn: 'root'})
export class SharedService {

    public artistForUpdate : Artist=null;
    public offerForUpdate : Offer = null;
    public report : FinalReport = null;

  constructor(private http: HttpClient) {
    
  }

}