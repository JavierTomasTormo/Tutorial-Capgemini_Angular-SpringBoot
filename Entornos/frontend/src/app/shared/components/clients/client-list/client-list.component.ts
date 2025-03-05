import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Client } from '../../../../core/models/client/client.model';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { ClientService } from '../../../../core/services/client/client.service';
import { ClientFormComponent } from '../client-form/client-form.component';
import { DialogConfirmationComponent } from '../../dialogs/dialog-confirmation/dialog-confirmation.component';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatTableModule,
    MatDialogModule,
    CommonModule
  ],
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.scss']
})
export class ClientListComponent implements OnInit {
  dataSource = new MatTableDataSource<Client>();
  displayedColumns: string[] = ['id', 'name', 'action'];

  constructor(
    private clientService: ClientService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.clientService.getAll().subscribe(clients => 
      this.dataSource.data = clients
    );
  }

  createClient(): void {
    const dialogRef = this.dialog.open(ClientFormComponent, { data: {} });
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.loadClients();
    });
  }

  editClient(client: Client): void {
    const dialogRef = this.dialog.open(ClientFormComponent, { data: client });
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.loadClients();
    });
  }

  deleteClient(client: Client): void {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
        data: { 
            title: "Eliminar Cliente", 
            description: "Al aceptar esto estas eliminando un cliente, ¿Estás seguro?"
        }
    });
    dialogRef.afterClosed().subscribe(confirmed => {
        if (confirmed) {
            this.clientService.delete(client.id!).subscribe({
              next: () => {
                this.loadClients();
              }
            });
        }
    });
  }
}