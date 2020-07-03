import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/services/film.service';
import { FilmDto } from 'src/app/dto/film.dto';
import { ArtistService } from 'src/app/services/artist.service';
import { Artist } from 'src/app/model/artist.model';
import { Message } from 'src/app/dto/message';
import { MessageComponent } from 'src/app/pages/message/message.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-create-film',
  templateUrl: './create-film.component.html',
  styleUrls: ['./create-film.component.css']
})
export class CreateFilmComponent implements OnInit {
  private film: FilmDto =
  {
    id: "",
    name: "",
    description:"",
    genre: "",
    year:0,
    actorIds:[],
    directorId: "",
    duration: 0,
    poster: "",
    writtenId: "",
  }
  private artists: Array<Artist>=[];

  constructor(private filmService: FilmService, private artistService: ArtistService, private modalService: NgbModal) { }

  ngOnInit() {
    this.artistService.getArtists().subscribe(data => {
      this.artists=data;
      console.log(this.artists.length);
  });

  }

  createFilm(){
    if(this.film.name!="" && this.film.description!="" && this.film.year >1800 && this.film.year < 2023 && this.film.duration > 10 && this.film.duration < 400 
    && this.film.poster!="" ){
    var e = document.getElementById("actors");
    let indexToFind = (<HTMLSelectElement> e);
    let selectedValues = Array.from(indexToFind.selectedOptions)
        .map(option => option.value)
    this.film.actorIds = selectedValues;
    var e2 = document.getElementById("director");
    let indexToFind2 = (<HTMLSelectElement> e2);
    this.film.directorId = indexToFind2.options[indexToFind2.selectedIndex].value;
    var e3 = document.getElementById("director");
    let indexToFind3 = (<HTMLSelectElement> e3);
    this.film.writtenId = indexToFind3.options[indexToFind3.selectedIndex].value;
    var e4 = document.getElementById("chosen");
    let indexToFind4 = (<HTMLSelectElement> e4);
    this.film.genre= indexToFind4.options[indexToFind4.selectedIndex].text;

    this.filmService.createFilm(this.film).subscribe(data => {
      var message : Message = {header:data.header, message:data.message, color:"green"};
      localStorage.setItem('message', JSON.stringify(message));
      const modalRef = this.modalService.open(MessageComponent);
      localStorage.removeItem("message2");
  });
}
  }
}
