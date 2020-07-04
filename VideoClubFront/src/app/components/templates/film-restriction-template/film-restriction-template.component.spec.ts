import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmRestrictionTemplateComponent } from './film-restriction-template.component';

describe('FilmRestrictionTemplateComponent', () => {
  let component: FilmRestrictionTemplateComponent;
  let fixture: ComponentFixture<FilmRestrictionTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilmRestrictionTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilmRestrictionTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
