import { Component, OnInit } from '@angular/core';
import { Film } from 'src/app/model/film.model';
import { FilmService } from 'src/app/services/film.service';
import { RuleService } from 'src/app/services/rule.service';
import { FilmRestriction } from 'src/app/dto/film-restriction-template';
import { FilmAgeRestrictionTemplate } from 'src/app/model/film-age-restriction-template.model';

@Component({
  selector: 'app-film-restriction-template',
  templateUrl: './film-restriction-template.component.html',
  styleUrls: ['./film-restriction-template.component.css']
})
export class FilmRestrictionTemplateComponent implements OnInit {

  private films : Film[] = [];
  private restrictions : FilmAgeRestrictionTemplate[] ;

  constructor(private filmService : FilmService, private ruleService : RuleService) { }

  ngOnInit() {
    var filmRes : FilmAgeRestrictionTemplate = {ageCategory : "CHILD", id:"1"};
    this.restrictions.push(filmRes);
    this.initializeFilms();
  }

  initializeFilms(){
    this.filmService.getFilms().subscribe(
      films => {
        this.films = films;
      }
    )
  }

  saveChanges(){
    this.ruleService.restrictFilmByAge(this.restrictions).subscribe(
      response => {console.log(response)}
    )
  }

}
