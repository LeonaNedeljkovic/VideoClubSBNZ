import { Component, OnInit } from '@angular/core';
import { ActionEvent } from 'src/app/model/action-event.model';
import { ActionEventService } from 'src/app/services/action-event.service';

@Component({
  selector: 'app-action-show',
  templateUrl: './action-show.component.html',
  styleUrls: ['./action-show.component.css']
})
export class ActionShowComponent implements OnInit {

  private actionEvents : ActionEvent[] = [];

  constructor(private actionEventService : ActionEventService) { }

  ngOnInit() {
    this.getActionEvents();
  }

  getActionEvents(){
    this.actionEventService.getActionEvents().subscribe(
      (events:ActionEvent[]) => {
        this.actionEvents = events;
      }
    )
  }

  filterGoldActions(actionEvent : ActionEvent){
    let goldActions = [];
    actionEvent.actions.forEach(action => {
      if(action.titleRank == "GOLD"){
        goldActions.push(action);
      }
    });
    return goldActions;
  }

  filterSilverActions(actionEvent : ActionEvent){
    let silverActions = [];
    actionEvent.actions.forEach(action => {
      if(action.titleRank == "SILVER"){
        silverActions.push(action);
      }
    });
    return silverActions;
  }

  filterBronzeActions(actionEvent : ActionEvent){
    let bronzeActions = [];
    actionEvent.actions.forEach(action => {
      if(action.titleRank == "BRONZE"){
        bronzeActions.push(action);
      }
    });
    return bronzeActions;
  }

  filterNoneActions(actionEvent : ActionEvent){
    let noneActions = [];
    actionEvent.actions.forEach(action => {
      if(action.titleRank == "NONE"){
        noneActions.push(action);
      }
    });
    return noneActions;
  }

}
