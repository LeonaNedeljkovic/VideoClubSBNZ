import { Component, OnInit } from '@angular/core';
import { ArtistService } from 'src/app/services/artist.service';
import { Artist } from 'src/app/model/artist.model';
import { SharedService } from 'src/app/services/shared.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-all-actors',
  templateUrl: './show-all-actors.component.html',
  styleUrls: ['./show-all-actors.component.css']
})
export class ShowAllActorsComponent implements OnInit {

  private allArtists: Array<Artist>=[];
  constructor(private artistService: ArtistService, private sharedService: SharedService, private router:Router) { }

  ngOnInit() {
    this.artistService.getArtists().subscribe(data => {
        this.allArtists=data;
  });

  }

  updateArtist(artist){
    this.sharedService.artistForUpdate=artist;
    this.router.navigate(['dashboard/update-artist']);
    
  }

}
