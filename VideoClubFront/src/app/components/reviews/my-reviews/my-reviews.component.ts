import { Component, OnInit } from '@angular/core';
import { ReviewService } from 'src/app/services/review.service';
import { Review } from 'src/app/model/review.model';
import { FilmService } from 'src/app/services/film.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RatingComponent } from '../rating/rating.component';
import { DetailsFilmComponent } from '../../films/details-film/details-film.component';
import { CreateReviewComponent } from '../create-review/create-review.component';

@Component({
  selector: 'app-my-reviews',
  templateUrl: './my-reviews.component.html',
  styleUrls: ['./my-reviews.component.css']
})
export class MyReviewsComponent implements OnInit {

  private reviews : Review[] = [];

  constructor(private modalService: NgbModal, private reviewService : ReviewService) { }

  ngOnInit() {
    this.getReviews();
  }

  getReviews(){
    this.reviewService.getByUser().subscribe(
      (reviews) => {
        this.reviews = reviews;
      }
    )
  }

  rate(id:string){
    localStorage.setItem('film-rate', id);
    const modalRef = this.modalService.open(RatingComponent);
  }

  moreInfo(id:number){
    localStorage.setItem('film-details', id.toString());
    const modalRef = this.modalService.open(DetailsFilmComponent);
  }

  watchFilm(id:number){
    localStorage.setItem('film-review', id.toString());
    const modalRef = this.modalService.open(CreateReviewComponent);
  }

}
