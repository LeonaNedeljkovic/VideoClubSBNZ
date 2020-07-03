import { Component, OnInit } from '@angular/core';
import { Artist } from 'src/app/model/artist.model';
import { ArtistService } from 'src/app/services/artist.service';

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

  constructor(private artistService: ArtistService) { }

  ngOnInit() {
  }


  addActor(){
    if(this.artist.name!="" && this.artist.surname!=""){
      this.artistService.createArtist(this.artist).subscribe(data => {
        if(data.name==this.artist.name){
          console.log("USPEHHH");
        }
    });
    }
  }

}
