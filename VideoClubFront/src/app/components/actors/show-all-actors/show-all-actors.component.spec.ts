import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowAllActorsComponent } from './show-all-actors.component';

describe('ShowAllActorsComponent', () => {
  let component: ShowAllActorsComponent;
  let fixture: ComponentFixture<ShowAllActorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowAllActorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowAllActorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
