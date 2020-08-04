import { Component, OnInit } from "@angular/core";
import { RuleService } from "src/app/services/rule.service";
import { FreeMinutes } from "src/app/model/free-minutes.model";

@Component({
  selector: "app-age-title-free-minutes-template",
  templateUrl: "./age-title-free-minutes-template.component.html",
  styleUrls: ["./age-title-free-minutes-template.component.css"],
})
export class AgeTitleFreeMinutesTemplateComponent implements OnInit {
  private freeMinutes: FreeMinutes[] = [];
  private message: string = "";

  constructor(private rulesService: RuleService) {}

  ngOnInit() {
    this.addTemplate();
  }

  addTemplate() {
    var freeMinutes: FreeMinutes = {
      ageCategory: "CHILD",
      amount: 0,
      header: "Free Minutes for you!",
      body: "",
      title: "GOLD",
    };
    this.freeMinutes.push(freeMinutes);
  }

  removeTemplate(template: FreeMinutes) {
    this.freeMinutes = this.freeMinutes.filter((obj) => obj !== template);
  }

  save() {
    this.freeMinutes.forEach((element) => {
      element.body =
        "You got " + element.amount + " free minutes as gift from us!";
    });
    this.rulesService
      .freeMinutesByAgeAndTitle(this.freeMinutes)
      .subscribe((result) => {
        this.message = "Template successfully created!";
      });
  }
}
