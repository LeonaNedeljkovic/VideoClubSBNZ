import { Component, OnInit } from '@angular/core';
import { ActionEventDto } from 'src/app/dto/action-event.dto';
import { ActionDto } from 'src/app/dto/action.dto';
import { OfferService } from 'src/app/services/offer.service';
import { Offer } from 'src/app/model/offer.model';
import { ActionEventService } from 'src/app/services/action-event.service';
import { ActionEvent } from 'src/app/model/action-event.model';

@Component({
  selector: 'app-action-create',
  templateUrl: './action-create.component.html',
  styleUrls: ['./action-create.component.css']
})
export class ActionCreateComponent implements OnInit {

  private actionEvent : ActionEventDto = {id:"0", startDate:"", endDate:"", name:"", actions:[]};
  private offers : Offer[] = [];
  private genres : string[] = ['ACTION','ADVENTURE','ANIMATED','COMEDY','DOCUMENTARY','DRAMA','HISTORICAL','HORROR','MUSIC','SCIFI','THRILLER','WESTERN','FAMILY']
  private allowWarnings = false;

  constructor(private offerService : OfferService, private actionEventService : ActionEventService) { }

  ngOnInit() {
    this.addAction();
    this.getOffers();
  }

  addAction(){
    this.allowWarnings = false;
    var action : ActionDto = {id:"0", description:"", actionType:"DISCOUNT", rank:"GOLD", amount:0, genres:[], discountOffersIds:[]};
    this.actionEvent.actions.push(action);
  }

  removeAction(action : ActionDto){
    if(this.actionEvent.actions.length > 1){
      this.actionEvent.actions = this.actionEvent.actions.filter(obj => obj !== action);
    }
    else{

    }
  }

  actionTypeChanged(action : ActionDto){
    action.discountOffersIds = [];
    action.amount = 0;
    action.genres = []
  }

  getOffers(){
    this.offerService.getOffers().subscribe(
      (offers:Offer[]) => {
        this.offers = offers;
      }
    )
  }

  getSelectedOffers(action: ActionDto){
    let offerDto = [];
    this.offers.forEach(offer => {
      let _offer = offer;
      action.discountOffersIds.forEach(selected => {
        if(_offer.id == selected.toString()){
          offerDto.push(_offer);
        }
      });
    });
    return offerDto;
  }

  addOffer(action:ActionDto, offer){
    action.discountOffersIds.push(+offer.target.value);
  }

  removeOffer(action : ActionDto, selected){
    action.discountOffersIds = action.discountOffersIds.filter(obj => obj != selected);
  }

  addGenre(action : ActionDto, genre ){
    console.log(genre.target.value);
    action.genres.push(genre.target.value);
  }

  removeGenre(action : ActionDto, genre : string){
    console.log(genre);
    action.genres = action.genres.filter(obj => obj != genre);
  }

  actionIsUnique(action :ActionDto){
    let num = 0;
    this.actionEvent.actions.forEach(a => {
      if(a.actionType == action.actionType && a.rank == action.rank){
        num++;
      }
    });
    if(num <= 1){
      return true;
    }
    else{
      return false;
    }
  }

  validate(){
    let valid = true;
    if(this.actionEvent.name == "" || this.actionEvent.startDate == "" || this.actionEvent.endDate == ""){
      valid = false;
    }
    else{
      this.actionEvent.actions.forEach(action => {
        if(!(this.actionIsUnique(action))){
          valid = false;
        }
        else if(action.actionType == "DISCOUNT" && (action.discountOffersIds.length == 0 || action.amount == 0)){
          valid = false;
        }
        else if(action.actionType == "FREE_CONTENT" && (action.genres.length == 0)){
          valid = false;
        }
        else if(action.description == ""){
          valid = false;
        }
      });
    }
    return valid;
  }

  createActionEvent(){
    this.allowWarnings = true;
    if(this.validate()){
      this.actionEventService.createActionEvent(this.actionEvent).subscribe(
        (event:ActionEvent) => {
          console.log(event);
        }
      )
    }
  }

}
