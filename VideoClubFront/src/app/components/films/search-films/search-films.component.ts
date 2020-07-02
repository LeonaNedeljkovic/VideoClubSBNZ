import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/services/film.service';
import { Film } from 'src/app/model/film.model';
import { RecommendedFilm } from 'src/app/model/recommended-film.model';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DetailsFilmComponent } from '../details-film/details-film.component';
import { CreateReviewComponent } from '../../reviews/create-review/create-review.component';

@Component({
  selector: 'app-search-films',
  templateUrl: './search-films.component.html',
  styleUrls: ['./search-films.component.css']
})
export class SearchFilmsComponent implements OnInit {

  private films : Film[];
  private searchedFilm : string;
  private searchedGenre : string;

  constructor(private modalService: NgbModal, private router: Router, private filmService: FilmService) { }

  ngOnInit() {
    this.initializeFilms();
  }

  initializeFilms(){
    var parameter = localStorage.getItem('parameter');
    if(parameter == null || parameter == 'topRated'){
      this.showTopRated();
    }
    else if(parameter == 'mostPopular'){
      this.showAllMostPopular();
    }
    else if(parameter == 'recommended'){
      this.showAllRecommended();
    }
    else if(parameter == 'search'){
      var search = localStorage.getItem('searchdParameter');
      this.searchedFilm = search;
      this.showSearchedByName();
    }
    else if(parameter == 'genre'){
      var genre = localStorage.getItem('genreParameter');
      this.searchedGenre = genre;
      this.showSearchedByGenre();
    }
  }

  showTopRated(){
    this.filmService.getTopRated(100).subscribe(
      (films:Film[]) => {
        this.films = films;
      }
    )
  }

  showAllMostPopular(){
    this.filmService.getMostPopular(100).subscribe(
      (films:Film[]) => {
        this.films = films;
      }
    )
  }

  showAllRecommended(){
    this.filmService.filmsRecommended(100).subscribe(
      (films:RecommendedFilm[]) => {
        let recommendedFilms : Film[] = [];
        films.forEach(element => {
          recommendedFilms.push(element.film);
        });
        this.films = recommendedFilms;
      }
    )
  }

  showSearchedByName(){
    this.filmService.getByName(this.searchedFilm).subscribe(
      (films:Film[]) => {
        this.films = films;
      }
    )
  }

  showSearchedByGenre(){
    this.filmService.getByGenre(this.searchedGenre).subscribe(
      (films:Film[]) => {
        this.films = films;
      }
    )
  }

  mainpage(){
    this.router.navigate(['dashboard/films-show']);
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
