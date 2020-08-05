import { Component, OnInit } from '@angular/core';
import { Artist } from 'src/app/model/artist.model';
import { ArtistService } from 'src/app/services/artist.service';
import { Message } from "src/app/dto/message";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { MessageComponent } from "src/app/pages/message/message.component";

@Component({
  selector: 'app-add-actor',
  templateUrl: './add-actor.component.html',
  styleUrls: ['./add-actor.component.css']
})
export class AddActorComponent implements OnInit {

  private artist: Artist ={
    id: "",
    name : "",
    surname:"",
    roles: [],
    written:[],
    directed:[]
  };

  constructor(private artistService: ArtistService,private modalService: NgbModal) { }

  ngOnInit() {
  }


  addActor(){
    if(this.artist.name!="" && this.artist.surname!=""){
      this.artistService.createArtist(this.artist).subscribe(data => {
        if(data.name==this.artist.name){
          var message: Message = {
            header: "Artist added!",
            message: "You have added artist successfully!",
            color: "green",
          };
          localStorage.setItem("message", JSON.stringify(message));
          const modalRef = this.modalService.open(MessageComponent);
        }
    });
    }
  }

}
