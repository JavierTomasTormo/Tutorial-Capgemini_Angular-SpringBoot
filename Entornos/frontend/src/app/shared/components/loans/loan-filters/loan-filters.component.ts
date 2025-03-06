import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';


@Component({
  selector: 'app-loan-filters',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './loan-filters.component.html',
  styleUrls: ['./loan-filters.component.scss']
})
export class LoanFiltersComponent {
  @Input() games: any[] = [];
  @Input() clients: any[] = [];
  @Input() filters: any = {};
  @Output() applyFilters = new EventEmitter<void>();
  @Output() clearFilters = new EventEmitter<void>();

  constructor(
    public router: Router
  ) { }

  onApplyFilters(): void {
    const loanDate = new Date(this.filters.loanDate);
    const returnDate = new Date(this.filters.returnDate);

    if (returnDate < loanDate) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'La fecha de fin no puede ser previa a la fecha de inicio.',
        confirmButtonText: 'Cerrar'
      });
      return;
    }

    const diffTime = Math.abs(returnDate.getTime() - loanDate.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays > 14) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'El período de préstamo máximo es de 14 días.',
        confirmButtonText: 'Cerrar'
      });
      return;
    }

    this.applyFilters.emit();
  }

  onClearFilters(): void {
    this.filters = {};
    this.clearFilters.emit();
  }
}