import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowFilmsComponent } from './show-films.component';

describe('ShowFilmsComponent', () => {
  let component: ShowFilmsComponent;
  let fixture: ComponentFixture<ShowFilmsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowFilmsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowFilmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
