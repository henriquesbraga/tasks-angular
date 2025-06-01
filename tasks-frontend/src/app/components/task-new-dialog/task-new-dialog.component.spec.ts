import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskNewDialogComponent } from './task-new-dialog.component';

describe('TaskNewDialogComponent', () => {
  let component: TaskNewDialogComponent;
  let fixture: ComponentFixture<TaskNewDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskNewDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskNewDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
