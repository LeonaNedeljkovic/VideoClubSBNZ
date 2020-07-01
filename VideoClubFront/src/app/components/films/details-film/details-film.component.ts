import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/services/film.service';
import { Film } from 'src/app/model/film.model';

@Component({
  selector: 'app-details-film',
  templateUrl: './details-film.component.html',
  styleUrls: ['./details-film.component.css']
})
export class DetailsFilmComponent implements OnInit {

  private film : Film;

  constructor(private filmService : FilmService) { }

  ngOnInit() {
    var id : number = +(localStorage.getItem('film-details'));
    this.getFilm(id);
  }

  getFilm(id:number){
    this.filmService.getFilmInfo(id).subscribe(
      (film:Film) => {
        this.film = film;
        this.initializeRating();
      }
    )
  }

  initializeRating(){
    if(this.film.rating >= 1 && this.film.rating < 1.5){
      var elem1 = document.getElementById('star1');
      elem1.style.content = "&#x2605;";
    }
    else if(this.film.rating >= 1.5 && this.film.rating < 2.5){
      document.getElementById('star1').style.content = "&#x2605;";
      document.getElementById('star2').style.content = "&#x2605;";
    }
    else if(this.film.rating >= 2.5 && this.film.rating < 3.5){
      document.getElementById('star1').style.content = "&#x2605;";
      document.getElementById('star2').style.content = "&#x2605;";
      document.getElementById('star3').style.content = "&#x2605;";
    }
    else if(this.film.rating >= 3.5 && this.film.rating < 4.5){
      document.getElementById("star1").style.content = "&#x2605;";
      document.getElementById("star2").style.content = "&#x2605;";
      document.getElementById("star3").style.content = "&#x2605;";
      document.getElementById("star4").style.content = "&#x2605;";
    }
    else if(this.film.rating >= 4.5){
      document.getElementById("star1").style.content = "&#x2605;";
      document.getElementById("star2").style.content = "&#x2605;";
      document.getElementById("star3").style.content = "&#x2605;";
      document.getElementById("star4").style.content = "&#x2605;";
      document.getElementById("star5").style.content = "&#x2605;";
    }
  }

}
