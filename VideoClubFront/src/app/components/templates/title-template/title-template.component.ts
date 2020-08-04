import { Component, OnInit } from "@angular/core";
import { RuleService } from "src/app/services/rule.service";
import { PointsDto } from "src/app/dto/points.dto";
import { TitleInfo } from "src/app/dto/title-info-dto";

@Component({
  selector: "app-title-template",
  templateUrl: "./title-template.component.html",
  styleUrls: ["./title-template.component.css"],
})
export class TitleTemplateComponent implements OnInit {
  private bronzeAcquire;
  private silverAcquire;
  private goldAcquire;

  private bronzeSave;
  private silverSave;
  private goldSave;

  private bronzeReward;
  private silverReward;
  private goldReward;

  private errorMessage: string = "";
  private greenMessage: string = "";

  constructor(private ruleService: RuleService) {}

  ngOnInit() {
    this.initializeTitleInfo();
  }

  initializeTitleInfo() {
    this.ruleService.getTitleInfo().subscribe((title: TitleInfo) => {
      this.bronzeAcquire = title.bronzeTitleAcquire;
      this.silverAcquire = title.silverTitleAcquire;
      this.goldAcquire = title.goldTitleAcquire;
      this.bronzeSave = title.bronzeTitleSave;
      this.silverSave = title.silverTitleSave;
      this.goldSave = title.goldTitleSave;
      this.bronzeReward = title.bronzeTitleReward;
      this.silverReward = title.silverTitleReward;
      this.goldReward = title.goldTitleReward;
    });
  }

  setBronzeAcquire() {
    var points: PointsDto = { value: this.bronzeAcquire };
    this.ruleService.setBronzeAquirePoints(points).subscribe((response) => {
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
    this.ruleService.setSilverAquirePoints(points).subscribe((response) => {
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
    this.ruleService.setGoldAquirePoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }

  setBronzeSave() {
    var points: PointsDto = { value: this.bronzeSave };
    this.ruleService.setBronzeSavePoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }

  setSilverSave() {
    var points: PointsDto = { value: this.silverSave };
    this.ruleService.setSilverSavePoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }

  setGoldSave() {
    var points: PointsDto = { value: this.goldSave };
    this.ruleService.setGoldSavePoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }

  setBronzeReward() {
    var points: PointsDto = { value: this.bronzeReward };
    this.ruleService.setBronzeRewardPoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }

  setSilverReward() {
    var points: PointsDto = { value: this.silverReward };
    this.ruleService.setSilverRewardPoints(points).subscribe((response) => {
      if (!response.value) {
        this.errorMessage = response.result;
        this.greenMessage = "";
      } else {
        this.errorMessage = "";
        this.greenMessage = "Updated successfully!";
      }
    });
  }

  setGoldReward() {
    var points: PointsDto = { value: this.goldReward };
    this.ruleService.setGoldRewardPoints(points).subscribe((response) => {
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
