import { Component, OnInit } from '@angular/core';
import { FinalReport } from 'src/app/dto/final-report';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-film-report',
  templateUrl: './film-report.component.html',
  styleUrls: ['./film-report.component.css']
})
export class FilmReportComponent implements OnInit {

  private report : FinalReport;

  constructor(private sharedService : SharedService) { }

  ngOnInit() {
    this.report = this.sharedService.report;
    console.log(this.report);
  }

}
