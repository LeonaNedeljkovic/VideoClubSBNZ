import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/services/film.service';
import { Film } from 'src/app/model/film.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { RatingComponent } from '../../reviews/rating/rating.component';
import { CreateReviewComponent } from '../../reviews/create-review/create-review.component';
import { ReviewDetails } from 'src/app/dto/review-details';
import { Review } from 'src/app/model/review.model';
import { ReviewService } from 'src/app/services/review.service';
import { CurrentUser } from 'src/app/model/currentUser';

@Component({
  selector: 'app-details-film',
  templateUrl: './details-film.component.html',
  styleUrls: ['./details-film.component.css']
})
export class DetailsFilmComponent implements OnInit {

  private film : Film;
  private loggedIn : boolean;
  private loggedUser;
  private id : number;
  private reviewDetails : ReviewDetails;
  private reviews : Review[] = [];

  constructor(private modalService: NgbModal, private router: Router, private filmService : FilmService, private reviewService : ReviewService) { }

  ngOnInit() {
    this.id = +(localStorage.getItem('film-details'));
    this.getFilm();

    this.loggedUser = JSON.parse(localStorage.getItem('currentUser'));
    if(this.loggedUser != null){
      this.loggedIn = true;
    }
    this.initReviewDetails();
  }

  getFilm(){
    this.filmService.getFilmInfo(this.id).subscribe(
      (film:Film) => {
        this.film = film;
      }
    )
  }

  rateFilm(){
    if(this.loggedIn){
      localStorage.setItem('film-rate', this.id.toString());
      const modalRef = this.modalService.open(RatingComponent);
    }
  }

  watchFilm(){
    if(this.loggedIn == true && this.loggedUser.role == 'ROLE_REGISTERED_USER'){
      localStorage.setItem('film-review', this.id.toString());
      const modalRef = this.modalService.open(CreateReviewComponent);
    }
  }

  initReviewDetails(){
    console.log(this.loggedUser);
    if(this.loggedIn == true && this.loggedUser.role == 'ROLE_REGISTERED_USER'){
      this.reviewService.getReviewDetails(this.id.toString()).subscribe(
        (details:ReviewDetails) => {
          this.reviewDetails = details;
        }
      )
    }
    else{
      this.reviewDetails = {startedWatching:false, addedToFavourites:false, watched:false, rate:0};
    }
  }

  favourites(id:string){
    if(this.loggedIn){
      this.filmService.addToFavourites(this.id.toString()).subscribe(
        
      )
      location.reload();
    }
  }

}
