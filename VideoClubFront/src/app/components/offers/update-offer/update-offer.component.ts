import { Component, OnInit } from '@angular/core';
import { OfferService } from 'src/app/services/offer.service';
import { SharedService } from 'src/app/services/shared.service';
import { Router } from '@angular/router';
import { Offer } from 'src/app/model/offer.model';
import { Message } from 'src/app/dto/message';
import { MessageComponent } from 'src/app/pages/message/message.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-update-offer',
  templateUrl: './update-offer.component.html',
  styleUrls: ['./update-offer.component.css']
})
export class UpdateOfferComponent implements OnInit {
  private offer: Offer={
    id: "",
    minutes : 0,
    price:0,
    discounts:[]
  };
  constructor(private offerService: OfferService, private sharedService: SharedService, private router: Router, private modalService: NgbModal) { }

  ngOnInit() {
    if(this.sharedService.offerForUpdate!=null){
      this.offer=this.sharedService.offerForUpdate;
    }else{
      this.router.navigate(['dashboard/show-all-offers']);
      

    }
  }

  updateOffer(){
    if(this.offer.price > 0 && this.offer.minutes > 0){
      this.offerService.updateOffer(this.offer.id, this.offer).subscribe(data => {
        if(this.offer.minutes=data.minutes){
          var message : Message = {header:"Offer updated!", message:"You have updated offer successfully!", color:"green"};
          localStorage.setItem('message', JSON.stringify(message));
          const modalRef = this.modalService.open(MessageComponent);
        }else{
          var message : Message = {header:"Update not successful!", message:"You have not updated offer successfully!", color:"green"};
          localStorage.setItem('message', JSON.stringify(message));
          const modalRef = this.modalService.open(MessageComponent);

        }
  });
    }else{
      var message : Message = {header:"Error!", message:"Price and minutes must be greater than 0!", color:"green"};
          localStorage.setItem('message', JSON.stringify(message));
          const modalRef = this.modalService.open(MessageComponent);

    }

  }

}
