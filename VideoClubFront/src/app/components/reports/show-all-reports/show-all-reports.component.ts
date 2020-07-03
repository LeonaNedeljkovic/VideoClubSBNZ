import { Component, OnInit } from '@angular/core';
import { RuleService } from 'src/app/services/rule.service';
import { Report } from 'src/app/model/report.model';

@Component({
  selector: 'app-show-all-reports',
  templateUrl: './show-all-reports.component.html',
  styleUrls: ['./show-all-reports.component.css']
})
export class ShowAllReportsComponent implements OnInit {

  private reports: Array<Report>=[];
  constructor(private ruleService: RuleService) { }

  ngOnInit() {
    this.ruleService.getAllReports().subscribe(data => {
      this.reports=data;
  });
}

}
