import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { LoanService } from '../../../../core/services/loan/loan.service';
import { Loan } from '../../../../core/models/loan/loan.model';
import { DialogConfirmationComponent } from '../../dialogs/dialog-confirmation/dialog-confirmation.component';
import { FormsModule } from '@angular/forms';
import { GameService } from '../../../../core/services/game/game.service';
import { ClientService } from '../../../../core/services/client/client.service';
import { LoanFiltersComponent } from '../loan-filters/loan-filters.component';
import { LoanListTableComponent } from '../loan-list-table/loan-list-table.component';


@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    LoanFiltersComponent,
    LoanListTableComponent
  ],
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss']
})
export class LoanListComponent implements OnInit {
  loans: Loan[] = [];
  games: any[] = [];
  clients: any[] = [];
  filters: any = {};

  constructor(
    private loanService: LoanService,
    private gameService: GameService,
    private clientService: ClientService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.loadLoans();
    this.loadGames();
    this.loadClients();
  }

  loadLoans(): void {
    this.loanService.search(this.filters).subscribe(data => {
      this.loans = data.map(loan => {
        const game = this.games.find(g => g.id === loan.gameId);
        const client = this.clients.find(c => c.id === loan.clientId);
        return {
          ...loan,
          gameTitle: game ? game.title : 'Unknown Game',
          clientName: client ? client.name : 'Unknown Client'
        };
      });
      // console.log('Loans', this.loans);  
    });
  }

  loadGames(): void {
    this.gameService.getAll().subscribe(data => {
      this.games = data;
      // console.log('Games', this.games);
    });
  }

  loadClients(): void {
    this.clientService.getAll().subscribe(data => {
      this.clients = data;
      // console.log('Clients', this.clients);
    });
  }

  applyFilters(): void {
    this.loadLoans();
  }

  clearFilters(): void {
    this.filters = {};
    this.loadLoans();
  }

  deleteLoan(loan: Loan): void {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      panelClass: 'confirmation-dialog-container',
      disableClose: true,
      autoFocus: false,
      data: { 
        title: "Eliminar Préstamo", 
        description: "¿Estás seguro de que deseas eliminar este préstamo? Esta acción no se puede deshacer." 
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loanService.delete(loan.id!).subscribe({
          next: () => {
            this.loadLoans();
          },
          error: (error) => {
            console.error('Error deleting loan:', error);
          }
        });
      }
    });
  }
}