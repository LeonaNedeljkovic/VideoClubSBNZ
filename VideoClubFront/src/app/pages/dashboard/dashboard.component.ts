import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor() {console.log("U dashboard component sam"); }

  ngOnInit() {
    console.log("U dashboard component sam");
  }

}
