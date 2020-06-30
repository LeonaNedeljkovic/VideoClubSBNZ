import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchFilmsComponent } from './search-films.component';

describe('SearchFilmsComponent', () => {
  let component: SearchFilmsComponent;
  let fixture: ComponentFixture<SearchFilmsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchFilmsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchFilmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
