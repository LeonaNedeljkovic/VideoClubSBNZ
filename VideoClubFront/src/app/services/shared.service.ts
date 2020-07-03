import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { Artist } from '../model/artist.model';

@Injectable({providedIn: 'root'})
export class SharedService {

    public artistForUpdate : Artist=null;

  constructor(private http: HttpClient) {
    
  }

}