import { Component, OnInit } from "@angular/core";
import { PointsDto } from "src/app/dto/points.dto";
import { RuleService } from "src/app/services/rule.service";
import { ImmunityInfo } from "src/app/dto/immunity-points-dto";

@Component({
  selector: "app-immunity-template",
  templateUrl: "./immunity-template.component.html",
  styleUrls: ["./immunity-template.component.css"],
})
export class ImmunityTemplateComponent implements OnInit {
  private bronzeAcquire;
  private silverAcquire;
  private goldAcquire;

  private errorMessage: string = "";
  private greenMessage: string = "";

  constructor(private ruleService: RuleService) {}

  ngOnInit() {
    this.initializeTitleInfo();
  }

  initializeTitleInfo() {
    this.ruleService.getImmunityInfo().subscribe((immunity: ImmunityInfo) => {
      this.bronzeAcquire = immunity.bronzeImmunityAcquire;
      this.silverAcquire = immunity.silverImmunityAcquire;
      this.goldAcquire = immunity.goldImmunityAcquire;
    });
  }

  setBronzeAcquire() {
    var points: PointsDto = { value: this.bronzeAcquire };
    this.ruleService.setBronzeImmunityPoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }

  setSilverAcquire() {
    var points: PointsDto = { value: this.silverAcquire };
    this.ruleService.setSilverImmunityPoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }

  setGoldAcquire() {
    var points: PointsDto = { value: this.goldAcquire };
    this.ruleService.setGoldImmunityPoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }
}
