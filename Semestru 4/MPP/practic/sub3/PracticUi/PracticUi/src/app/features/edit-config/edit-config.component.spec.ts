import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditConfigComponent } from './edit-config.component';

describe('EditConfigComponent', () => {
  let component: EditConfigComponent;
  let fixture: ComponentFixture<EditConfigComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditConfigComponent]
    });
    fixture = TestBed.createComponent(EditConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
