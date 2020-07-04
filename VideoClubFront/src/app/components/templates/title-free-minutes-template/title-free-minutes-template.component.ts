import { Component, OnInit } from '@angular/core';
import { RuleService } from 'src/app/services/rule.service';
import { FreeMinutes } from 'src/app/model/free-minutes.model';

@Component({
  selector: 'app-title-free-minutes-template',
  templateUrl: './title-free-minutes-template.component.html',
  styleUrls: ['./title-free-minutes-template.component.css']
})
export class TitleFreeMinutesTemplateComponent implements OnInit {

  private freeMinutes : FreeMinutes[] = []

  constructor(private rulesService : RuleService) { }

  ngOnInit() {
    var freeMinutes : FreeMinutes ={ageCategory : "CHILD", amount:0, header : "Free Minutes for you!", body: "", title: "GOLD"};
    this.freeMinutes.push(freeMinutes);
  }

  save(){
    this.freeMinutes.forEach(element => {
      element.body = "You got " + element.amount + " free minutes as gift from us!";
    });
    this.rulesService.freeMinutesByTitle(this.freeMinutes).subscribe(
      result =>{console.log(result)}
    )
  }
}
