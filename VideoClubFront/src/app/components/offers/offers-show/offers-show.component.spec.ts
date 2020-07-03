import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OffersShowComponent } from './offers-show.component';

describe('OffersShowComponent', () => {
  let component: OffersShowComponent;
  let fixture: ComponentFixture<OffersShowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OffersShowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OffersShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
