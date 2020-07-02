import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { ActionDto } from '../dto/action.dto';
import { Action } from '../model/action.model';
import { MessageDto } from '../dto/message.dto';
import { ReviewDto } from '../dto/review.dto';
import { Review } from '../model/review.model';
import { ReviewDetails } from '../dto/review-details';

@Injectable({providedIn: 'root'})
export class ReviewService {

  constructor(private http: HttpClient) {
    
  }

    saveReview = (data: ReviewDto): Observable<Review> => {
      return this.http.post<Review>("/api/review", data).pipe(
        map( (res: any) => {
            return res;
        })  );
    }

    getByUser = (): Observable<Review[]> => {
    return this.http.get<Review[]>(`/api/reviews`).pipe(
      map( (res: any) => {
          return res;
      })  );
    }
    
    getOne = (id:string): Observable<Review> => {
        return this.http.get<Review>(`/api/review/${id}`).pipe(
          map( (res: any) => {
              return res;
          })  );
    }

    getReviewDetails = (id:string): Observable<ReviewDetails> => {
      return this.http.get<Review>(`/api/review/details/${id}`).pipe(
        map( (res: any) => {
            return res;
        })  );
  }

}