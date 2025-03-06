import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoanService } from '../../../../core/services/loan/loan.service';
import { GameService } from '../../../../core/services/game/game.service';
import { ClientService } from '../../../../core/services/client/client.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-loan-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './loan-create.component.html',
  styleUrls: ['./loan-create.component.scss']
})
export class LoanCreateComponent implements OnInit {
  loanForm: FormGroup;
  games: any[] = [];
  clients: any[] = [];

  constructor(
    private fb: FormBuilder,
    private loanService: LoanService,
    private gameService: GameService,
    private clientService: ClientService,
    public router: Router
  ) {
    this.loanForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      gameId: ['', Validators.required],
      clientId: ['', Validators.required],
      loanDate: ['', Validators.required],
      returnDate: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadGames();
    this.loadClients();
  }

  loadGames(): void {
    this.gameService.getAll().subscribe(data => {
      this.games = data;
    });
  }

  loadClients(): void {
    this.clientService.getAll().subscribe(data => {
      this.clients = data;
    });
  }

  onSubmit(): void {
    if (this.loanForm.invalid) {
      return;
    }

    const loanData = this.loanForm.getRawValue();
    const loanDate = new Date(loanData.loanDate);
    const returnDate = new Date(loanData.returnDate);
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

    this.loanService.search({ gameId: loanData.gameId, loanDate: loanData.loanDate, returnDate: loanData.returnDate }).subscribe(existingLoans => {
      if (existingLoans.content.length > 0) {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'El mismo juego no puede estar prestado a dos clientes distintos en un mismo día.',
          confirmButtonText: 'Cerrar'
        });
        return;
      }

      this.loanService.save(loanData).subscribe({
        next: () => {
          this.router.navigate(['/loans']);
        },
        error: (error) => {
          console.error('Error creating loan:', error);
        }
      });
    });
  }
}