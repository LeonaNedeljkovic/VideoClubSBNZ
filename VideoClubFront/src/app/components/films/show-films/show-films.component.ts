import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/services/film.service';
import { Film } from 'src/app/model/film.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-films',
  templateUrl: './show-films.component.html',
  styleUrls: ['./show-films.component.css']
})
export class ShowFilmsComponent implements OnInit {

  private topRatedFilms : Film[];
  private mostPopularFilms : Film[];
  private searchedFilm : string;
  private searchedGenre : string;

  constructor(private router: Router, private filmService: FilmService) { }

  ngOnInit() {
    this.initializeTopRated(6);
    this.initializeMostPopulard(6);
  }

  initializeTopRated(num : number){
    this.filmService.getTopRated(6).subscribe(
      (films:Film[]) => {
        this.topRatedFilms = films;
      }
    )
  }

  initializeMostPopulard(num : number){
    this.filmService.getMostPopular(6).subscribe(
      (films:Film[]) => {
        this.mostPopularFilms = films;
      }
    )
  }

  seeAll(parameter:string){
    if(parameter == 'topRated'){
      localStorage.setItem('parameter', 'topRated');
    }
    else{
      localStorage.setItem('parameter', 'mostPopular');
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

}
