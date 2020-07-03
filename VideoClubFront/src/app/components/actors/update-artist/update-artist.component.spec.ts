import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateArtistComponent } from './update-artist.component';

describe('UpdateArtistComponent', () => {
  let component: UpdateArtistComponent;
  let fixture: ComponentFixture<UpdateArtistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateArtistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateArtistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
