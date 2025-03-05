import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanListTableComponent } from './loan-list-table.component';

describe('LoanListTableComponent', () => {
  let component: LoanListTableComponent;
  let fixture: ComponentFixture<LoanListTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoanListTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanListTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
