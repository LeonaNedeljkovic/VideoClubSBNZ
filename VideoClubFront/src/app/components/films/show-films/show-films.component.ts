import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/services/film.service';
import { Film } from 'src/app/model/film.model';
import { Router } from '@angular/router';
import { RecommendedFilm } from 'src/app/model/recommended-film.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DetailsFilmComponent } from '../details-film/details-film.component';
import { CreateReviewComponent } from '../../reviews/create-review/create-review.component';

@Component({
  selector: 'app-show-films',
  templateUrl: './show-films.component.html',
  styleUrls: ['./show-films.component.css']
})
export class ShowFilmsComponent implements OnInit {

  private topRatedFilms : Film[];
  private mostPopularFilms : Film[];
  private recommendedFilms : RecommendedFilm[];
  private searchedFilm : string;
  private searchedGenre : string;
  private loggedIn : boolean  = false;

  constructor(private modalService: NgbModal, private router: Router, private filmService: FilmService) { }

  ngOnInit() {
    var loogedUser = JSON.parse(localStorage.getItem('currentUser'));
    if(loogedUser != null){
      this.loggedIn = true;
      this.initializeRecommended(6);
    }

    this.initializeTopRated(6);
    this.initializeMostPopulard(6);
  }

  initializeTopRated(num : number){
    this.filmService.getTopRated(num).subscribe(
      (films:Film[]) => {
        this.topRatedFilms = films;
      }
    )
  }

  initializeMostPopulard(num : number){
    this.filmService.getMostPopular(num).subscribe(
      (films:Film[]) => {
        this.mostPopularFilms = films;
      }
    )
  }

  initializeRecommended(num : number){
    this.filmService.filmsRecommended(num).subscribe(
      (films:RecommendedFilm[]) => {
        this.recommendedFilms = films;
      }
    )
  }

  seeAll(parameter:string){
    if(parameter == 'topRated'){
      localStorage.setItem('parameter', 'topRated');
    }
    else if(parameter == 'mostPopular'){
      localStorage.setItem('parameter', 'mostPopular');
    }
    else{
      localStorage.setItem('parameter', 'recommended');
    }
    this.router.navigate(['dashboard/films-search']);
  }

  search(){
    localStorage.setItem('parameter', 'search');
    localStorage.setItem('searchdParameter', this.searchedFilm);
    this.router.navigate(['dashboard/films-search']);
  }

  filter(){
    localStorage.setItem('parameter', 'genre');
    localStorage.setItem('genreParameter', this.searchedGenre);
    this.router.navigate(['dashboard/films-search']);
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
