import { Component, OnInit } from "@angular/core";
import { RuleService } from "src/app/services/rule.service";
import { FreeContent } from "src/app/model/free-content.model";
import { FreeMinutes } from "src/app/model/free-minutes.model";

@Component({
  selector: "app-age-free-minutes-template",
  templateUrl: "./age-free-minutes-template.component.html",
  styleUrls: ["./age-free-minutes-template.component.css"],
})
export class AgeFreeMinutesTemplateComponent implements OnInit {
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
      title: "NONE",
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
    console.log(this.freeMinutes);
    this.rulesService.freeMinutesByAge(this.freeMinutes).subscribe((result) => {
      this.message = "Template successfully created!";
    });
  }
}
