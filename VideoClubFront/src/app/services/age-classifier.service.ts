import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { AgeClassifier } from '../model/age-classifier.model';

@Injectable({providedIn: 'root'})
export class AgeClassifierService {

  constructor(private http: HttpClient) {
    
  }

    getAll = (): Observable<AgeClassifier[]> => {
      return this.http.get<AgeClassifier[]>("/api/age_classifiers").pipe(
        map( (res: any) => {
            return res;
        })  );
    }
}