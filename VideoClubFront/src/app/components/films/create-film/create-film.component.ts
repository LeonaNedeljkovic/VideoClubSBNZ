import { Component, OnInit } from "@angular/core";
import { FilmService } from "src/app/services/film.service";
import { FilmDto } from "src/app/dto/film.dto";
import { ArtistService } from "src/app/services/artist.service";
import { Artist } from "src/app/model/artist.model";
import { Message } from "src/app/dto/message";
import { MessageComponent } from "src/app/pages/message/message.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { SharedService } from "src/app/services/shared.service";
import { FinalReport } from "src/app/dto/final-report";
import { FilmReportComponent } from "../film-report/film-report.component";

@Component({
  selector: "app-create-film",
  templateUrl: "./create-film.component.html",
  styleUrls: ["./create-film.component.css"],
})
export class CreateFilmComponent implements OnInit {
  private searchInput: string = "";
  private searchedArtist: Artist[] = [];
  private searchInputDirector: string = "";
  private searchInputScenarist: string = "";
  private searchedDirectors: Artist[] = [];
  private searchedScenarist: Artist[] = [];
  private loggedIn: boolean;
  private loggedUser;
  private film: FilmDto = {
    id: "0",
    name: "",
    description: "",
    genre: "",
    year: 0,
    actorIds: [],
    directorId: "",
    duration: 0,
    poster: "",
    writtenId: "",
  };
  private artists: Array<Artist> = [];

  constructor(
    private filmService: FilmService,
    private artistService: ArtistService,
    private modalService: NgbModal,
    private sharedService: SharedService
  ) {}

  ngOnInit() {
    this.artistService.getArtists().subscribe((data) => {
      this.artists = data;
      console.log(this.artists.length);
    });
    this.loggedUser = JSON.parse(localStorage.getItem("currentUser"));
    if (this.loggedUser != null) {
      this.loggedIn = true;
    }
  }

  getSelectedActors() {
    let artistDto = [];
    this.artists.forEach((artist) => {
      let _artist = artist;
      this.film.actorIds.forEach((selected) => {
        if (_artist.id == selected.toString()) {
          artistDto.push(_artist);
        }
      });
    });
    return artistDto;
  }

  addActor(actor: Artist) {
    this.film.actorIds.push(actor.id + "");
    this.searchedArtist = [];
  }

  addDirector(director: Artist) {
    this.film.directorId = director.id;
    this.searchInputDirector = director.name + " " + director.surname;
    this.searchedDirectors = [];
  }

  addScenarist(scenarist: Artist) {
    this.film.writtenId = scenarist.id;
    this.searchInputScenarist = scenarist.name + " " + scenarist.surname;
    this.searchedScenarist = [];
  }

  removeActor(selected) {
    this.film.actorIds = this.film.actorIds.filter((obj) => obj != selected);
  }

  getReport() {
    if (
      this.film.name != "" &&
      this.film.year > 1800 &&
      this.film.year < 2023 &&
      this.film.duration > 10 &&
      this.film.duration < 400 &&
      this.film.poster != ""
    ) {
      var e4 = document.getElementById("chosen");
      let indexToFind4 = <HTMLSelectElement>e4;
      this.film.genre = indexToFind4.options[indexToFind4.selectedIndex].text;
      this.filmService.filmReport(this.film).subscribe((data) => {
        var film: FilmDto = this.film;
        this.sharedService.filmToAdd = film;
        var report: FinalReport = data;
        this.sharedService.report = report;
        const modalRef = this.modalService.open(FilmReportComponent);
      });
    } else {
      var message: Message = {
        header: "Error",
        message: "All fields must be field in",
        color: "green",
      };
      localStorage.setItem("message", JSON.stringify(message));
      const modalRef = this.modalService.open(MessageComponent);
    }
  }

  searchArtist() {
    if (this.searchInput != "") {
      this.searchedArtist = this.artists.filter((artist: Artist) => {
        return (artist.name + " " + artist.surname)
          .toLowerCase()
          .includes(this.searchInput.toLowerCase());
      });
    } else {
      this.searchedArtist = [];
      return this.searchedArtist;
    }
  }

  searchScenarist() {
    if (this.searchInputScenarist != "") {
      this.searchedScenarist = this.artists.filter((artist: Artist) => {
        return (artist.name + " " + artist.surname)
          .toLowerCase()
          .includes(this.searchInputScenarist.toLowerCase());
      });
    } else {
      this.searchedScenarist = [];
      return this.searchedScenarist;
    }
  }

  searchDirector() {
    if (this.searchInputDirector != "") {
      this.searchedDirectors = this.artists.filter((artist: Artist) => {
        return (artist.name + " " + artist.surname)
          .toLowerCase()
          .includes(this.searchInputDirector.toLowerCase());
      });
    } else {
      this.searchedDirectors = [];
      return this.searchedDirectors;
    }
  }
}
