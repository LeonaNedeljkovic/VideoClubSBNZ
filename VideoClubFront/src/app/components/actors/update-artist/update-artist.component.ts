import { Component, OnInit } from '@angular/core';
import { ArtistService } from 'src/app/services/artist.service';
import { SharedService } from 'src/app/services/shared.service';
import { Artist } from 'src/app/model/artist.model';
import { Router } from '@angular/router';
import { Message } from "src/app/dto/message";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { MessageComponent } from "src/app/pages/message/message.component";

@Component({
  selector: 'app-update-artist',
  templateUrl: './update-artist.component.html',
  styleUrls: ['./update-artist.component.css']
})
export class UpdateArtistComponent implements OnInit {

  private artist: Artist={
    id: "",
    name : "",
    surname:"",
    roles: [],
    written:[],
    directed:[]
  };
  constructor(private artistService: ArtistService, private sharedService: SharedService, private router: Router,private modalService: NgbModal) { }

  ngOnInit() {
    if(this.sharedService.artistForUpdate!=null){
      this.artist=this.sharedService.artistForUpdate;
    }else{
      this.router.navigate(['dashboard/show-all-actors']);
      

    }
  }

  updateArtist(){
    if(this.artist.name!=null && this.artist.name!="" && this.artist.surname!=null && this.artist.surname!=""){
      this.artistService.updateArtist(this.artist).subscribe(data => {
        if(this.artist.name=data.name){
          var message: Message = {
            header: "Artist updated!",
            message: "You have updated artist successfully!",
            color: "green",
          };
          localStorage.setItem("message", JSON.stringify(message));
          const modalRef = this.modalService.open(MessageComponent);
        }
  });
    }

  }

}
