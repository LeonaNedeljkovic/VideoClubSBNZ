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
  private restrictions : FilmAgeRestrictionTemplate[] = [];
  private message : string = "";

  constructor(private filmService : FilmService, private ruleService : RuleService) { }

  ngOnInit() {
    this.addTemplate();
    this.initializeFilms();
  }

  initializeFilms(){
    this.filmService.getFilms().subscribe(
      films => {
        this.films = films;
      }
    )
  }

  addTemplate(){
    var filmRes : FilmAgeRestrictionTemplate = {ageCategory : "CHILD", id:"1"};
    this.restrictions.push(filmRes);
  }

  removeTemplate(template: FilmAgeRestrictionTemplate){
    this.restrictions = this.restrictions.filter(obj => obj !== template);
  }

  saveChanges(){
    this.ruleService.restrictFilmByAge(this.restrictions).subscribe(
      response =>{this.message="Template successfully created!"}
    )
  }

}
