import { Component, OnInit } from '@angular/core';
import { GenreAgeRestrictionTemplate } from 'src/app/model/genre-age-restriction-tempale.model';
import { RuleService } from 'src/app/services/rule.service';

@Component({
  selector: 'app-genre-restriction-template',
  templateUrl: './genre-restriction-template.component.html',
  styleUrls: ['./genre-restriction-template.component.css']
})
export class GenreRestrictionTemplateComponent implements OnInit {

  private restriction : GenreAgeRestrictionTemplate = {genre:"HORROR", ageCategory:"CHILD" }
  constructor(private ruleService : RuleService) { }

  ngOnInit() {
  }

  saveChanges(){
    console.log(this.restriction);
    var template : GenreAgeRestrictionTemplate[] = [this.restriction];
    this.ruleService.restrictGenreByAge(template).subscribe(
      response => {console.log(response)}
    )
  }

}
