import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-list-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './loan-list-table.component.html',
  styleUrls: ['./loan-list-table.component.scss']
})
export class LoanListTableComponent {
  @Input() loans: any[] = [];
  @Output() deleteLoan = new EventEmitter<any>();

  onDeleteLoan(loan: any): void {
    this.deleteLoan.emit(loan);
  }
}