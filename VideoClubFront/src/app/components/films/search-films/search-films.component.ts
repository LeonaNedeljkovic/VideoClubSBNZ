import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/services/film.service';
import { Film } from 'src/app/model/film.model';

@Component({
  selector: 'app-search-films',
  templateUrl: './search-films.component.html',
  styleUrls: ['./search-films.component.css']
})
export class SearchFilmsComponent implements OnInit {

  private films : Film[];
  private searchedFilm : string;
  private searchedGenre : string;

  constructor(private filmService: FilmService) { }

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

}
