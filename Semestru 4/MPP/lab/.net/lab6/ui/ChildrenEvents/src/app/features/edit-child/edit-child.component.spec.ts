import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditChildComponent } from './edit-child.component';

describe('EditChildComponent', () => {
  let component: EditChildComponent;
  let fixture: ComponentFixture<EditChildComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditChildComponent]
    });
    fixture = TestBed.createComponent(EditChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
