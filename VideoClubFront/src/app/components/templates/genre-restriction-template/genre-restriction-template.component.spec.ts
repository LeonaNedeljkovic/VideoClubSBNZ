import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenreRestrictionTemplateComponent } from './genre-restriction-template.component';

describe('GenreRestrictionTemplateComponent', () => {
  let component: GenreRestrictionTemplateComponent;
  let fixture: ComponentFixture<GenreRestrictionTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenreRestrictionTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenreRestrictionTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
