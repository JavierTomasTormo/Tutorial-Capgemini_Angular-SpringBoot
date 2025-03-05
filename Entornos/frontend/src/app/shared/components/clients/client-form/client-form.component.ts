import { Component, OnInit, OnDestroy, Inject, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClientService } from '../../../../core/services/client/client.service';
import { Client } from '../../../../core/models/client/client.model';

@Component({
    selector: 'app-client-form',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './client-form.component.html',
    styleUrls: ['./client-form.component.scss']
})
export class ClientFormComponent implements OnInit, OnDestroy {
    client: Client;
    nameError: string | null = null;

    constructor(
        public dialogRef: MatDialogRef<ClientFormComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private clientService: ClientService
    ) {}

    ngOnInit(): void {
        document.body.classList.add('modal-open');
        
        if (this.data && this.data.id !== undefined) {
            this.client = new Client();
            this.client.id = this.data.id;
            this.client.name = this.data.name;
        } else if (this.data && this.data.client) {
            this.client = Object.assign(new Client(), this.data.client);
        } else {
            this.client = new Client();
        }
    }

    ngOnDestroy(): void {
        document.body.classList.remove('modal-open');
    }

    onSave(): void {
        if (!this.client.name || this.client.name.trim() === '') {
            this.nameError = 'El nombre del cliente es obligatorio';
            return;
        }

        if (this.client.id) {
            this.clientService.update(this.client.id, this.client).subscribe({
                next: () => {
                    this.dialogRef.close(true);
                },
                error: (error) => {
                    this.nameError = 'Ya existe un cliente con este nombre';
                }
            });
        } else {
            this.clientService.create(this.client).subscribe({
                next: () => {
                    this.dialogRef.close(true);
                },
                error: (error) => {
                    this.nameError = 'Ya existe un cliente con este nombre';
                }
            });
        }
    }

    onClose(): void {
        this.dialogRef.close(false);
    }
  
    onBackdropClick(event: MouseEvent): void {
        if ((event.target as HTMLElement).className === 'modal-overlay') {
            this.onClose();
        }
    }
  
    @HostListener('document:keydown.escape')
    onEscapePress(): void {
        this.onClose();
    }
}