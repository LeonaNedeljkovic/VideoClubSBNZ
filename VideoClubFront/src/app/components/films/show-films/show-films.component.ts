import { Component, OnInit } from '@angular/core';
import { FilmService } from 'src/app/services/film.service';
import { Film } from 'src/app/model/film.model';
import { Router } from '@angular/router';
import { RecommendedFilm } from 'src/app/model/recommended-film.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DetailsFilmComponent } from '../details-film/details-film.component';
import { CreateReviewComponent } from '../../reviews/create-review/create-review.component';
import { CurrentUser } from 'src/app/model/currentUser';
import { Artist } from 'src/app/model/artist.model';
import { ArtistService } from 'src/app/services/artist.service';
import { SharedService } from 'src/app/services/shared.service';

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
  private loggedUser;
  private searchedArtist : Artist[] = [];
  private searchedArtistID : string = "";
  private allArtists : Artist[] = [];
  private searchInput : string = "";

  private searched : boolean = false;
  private films : Film[] = [];

  constructor(private modalService: NgbModal, private router: Router, private filmService: FilmService,
    private artistService: ArtistService, private sharedService: SharedService) { }

  ngOnInit() {
    this.loggedUser = JSON.parse(localStorage.getItem('currentUser'));
    if(this.loggedUser != null){
      this.loggedIn = true;
      this.initializeRecommended(6);
    }

    this.initializeTopRated(6);
    this.initializeMostPopulard(6);
    this.initializeArtists();
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

  initializeArtists(){
    this.artistService.getArtists().subscribe(
      (allArtists:Artist[]) => {
        this.allArtists = allArtists;
      }
    )
  }

  seeAll(parameter:string){
    if(parameter == 'topRated'){
      this.showTopRated();
    }
    else if(parameter == 'mostPopular'){
      this.showAllMostPopular();
    }
    else{
      this.showAllRecommended();
    }
  }

  search(){
    this.showSearchedByName();
  }

  searchArtist() {
    if(this.searchInput != ""){
      this.searchedArtist = this.allArtists.filter((artist: Artist) => {
        return (artist.name + " " + artist.surname).toLowerCase().includes(this.searchInput.toLowerCase());
      });
    }
    else{
      this.searchedArtist = [];
      return this.searchedArtist;
    }
  }

  selectArtist(artist:Artist) {
    this.searchedArtistID = artist.id;
    this.searchInput = artist.name + " " + artist.surname;
    this.searchedArtist = [];
  }

  recommendFilmByArtist(){
    if(this.searchedArtistID == ""){
      if(this.searchedArtist.length != 1){
        console.log("Dobro nije");
      }
      else{
        this.showSearchedByArtist(this.searchedArtist[0].id);
      }
    }
    else{
      this.showSearchedByArtist(this.searchedArtistID);
    }
  }

  showTopRated(){
    this.filmService.getTopRated(100).subscribe(
      (films:Film[]) => {
        this.films = films;
      }
    )
    this.searched = true;
  }

  showAllMostPopular(){
    this.filmService.getMostPopular(100).subscribe(
      (films:Film[]) => {
        this.films = films;
      }
    )
    this.searched = true;
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
    this.searched = true;
  }

  showSearchedByName(){
    this.filmService.getByName(this.searchedFilm).subscribe(
      (films:Film[]) => {
        this.films = films;
      }
    )
    this.searched = true;
  }

  showSearchedByGenre(){
    this.filmService.getByGenre(this.searchedGenre).subscribe(
      (films:Film[]) => {
        this.films = films;
      }
    )
    this.searched = true;
  }

  showSearchedByArtist(id:string){
    this.films = [];
    this.filmService.filmsRecommendedByArtist(100, id).subscribe(
      (recommended:RecommendedFilm[]) => {
        recommended.forEach(element => {
          this.films.push(element.film);
        });
      }
    )
    this.searched = true;
  }

  mainpage(){
    this.searched = false;
  }

  moreInfo(id:number){
    localStorage.setItem('film-details', id.toString());
    const modalRef = this.modalService.open(DetailsFilmComponent);
  }

  watchFilm(id:number){
    if(this.loggedIn == true && this.loggedUser.role === 'ROLE_REGISTERED_USER'){
      localStorage.setItem('film-review', id.toString());
    const modalRef = this.modalService.open(CreateReviewComponent);
    }
    else{
      this.moreInfo(id);
    }
  }

}
