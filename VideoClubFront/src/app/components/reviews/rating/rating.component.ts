import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbActiveModal  } from '@ng-bootstrap/ng-bootstrap';
import { FilmService } from 'src/app/services/film.service';
import { ReviewService } from 'src/app/services/review.service';
import { Film } from 'src/app/model/film.model';
import { Review } from 'src/app/model/review.model';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {

  private rated : number = 0;
  private loggedIn : boolean = false;
  private id : number;
  private successMessage : String = "";
  private failureMessage : String = "";
  private reviews : Review[] = [];

  constructor(private modalService: NgbModal, private filmService : FilmService, private reviewService : ReviewService,private activeModal: NgbActiveModal) { }

  ngOnInit() {
    this.id = +(localStorage.getItem('film-rate'));

    var loogedUser = JSON.parse(localStorage.getItem('currentUser'));
    if(loogedUser != null){
      this.loggedIn = true;
    }
    this.initReviews();
  }

  rate(num:number){
    this.rated = num;
  }

  initReviews(){
    this.reviewService.getByUser().subscribe(
      (reviews:Review[]) => {
        this.reviews = reviews;
      }
    )
  }

  checkReview(){
    let filmId = this.id;
    let watched = false;
    this.reviews.forEach(review => {
      if(review.film.id == filmId.toString() && review.watched == true){
        watched = true;
      }
    });
    if(watched == false){
      this.failureMessage = "You have to watch film first before giving it a rate.";
      this.successMessage = "";
    }
    return watched;
  }

  checkRate(){
    if(this.rated == 0){
      this.failureMessage = "Please enter your rate.";
      this.successMessage = "";
      return false;
    }
    else{
      return true;
    }
  }

  submitRate(){
    if(this.checkRate() == true && this.checkReview() == true){
      this.filmService.rateFilm(this.id.toString(), this.rated).subscribe(
        (film:Film) => {
          this.activeModal.dismiss();
        }
      )
      this.failureMessage = "";
      this.successMessage = "Your rate is saved!";
    }
  }

}
