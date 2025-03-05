import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanFiltersComponent } from './loan-filters.component';

describe('LoanFiltersComponent', () => {
  let component: LoanFiltersComponent;
  let fixture: ComponentFixture<LoanFiltersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoanFiltersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
