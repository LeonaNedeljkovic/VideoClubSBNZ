import { Component, OnInit } from '@angular/core';
import { AgeClassifierService } from 'src/app/services/age-classifier.service';
import { AgeClassifier } from 'src/app/model/age-classifier.model';
import { RuleService } from 'src/app/services/rule.service';

@Component({
  selector: 'app-age-template',
  templateUrl: './age-template.component.html',
  styleUrls: ['./age-template.component.css']
})
export class AgeTemplateComponent implements OnInit {

  private childClassifier : AgeClassifier;
  private teenClassifier : AgeClassifier;
  private youngAdultClassifier : AgeClassifier;
  private adultClassifier : AgeClassifier;
  private elderClassifier : AgeClassifier;

  constructor(private ageClassifierService : AgeClassifierService, private ruleService : RuleService) { }

  ngOnInit() {
    this.initAgeClassifiers();
  }

  initAgeClassifiers(){
    this.ageClassifierService.getAll().subscribe(
      (response) => {
        response.forEach(classifier => {
          if(classifier.ageCategory == 'CHILD'){
            this.childClassifier = classifier;
          }
          else if(classifier.ageCategory == 'TEEN'){
            this.teenClassifier = classifier;
          }
          else if(classifier.ageCategory == 'YOUNG_ADULT'){
            this.youngAdultClassifier = classifier;
          }
          else if(classifier.ageCategory == 'ADULT'){
            this.adultClassifier = classifier;
          }
          else{
            this.elderClassifier = classifier;
          }
        });
      }
    )
  }

  saveAgeClassifiers(){
    let classifiers = [this.childClassifier, this.teenClassifier, this.youngAdultClassifier,
       this.adultClassifier, this.elderClassifier];
    this.ruleService.classifyUserByAge(classifiers).subscribe(
      response => {
        console.log(response)
      }
    )
  }

}
