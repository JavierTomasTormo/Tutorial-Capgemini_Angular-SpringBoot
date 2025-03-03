import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClientService } from '../../../../core/services/client/client.service';
import { Client } from '../../../../core/models/client/client.model';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common'; 

@Component({
    selector: 'app-client-form',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule, 
        MatFormFieldModule, 
        MatInputModule, 
        MatButtonModule
    ],
    templateUrl: './client-form.component.html',
    styleUrls: ['./client-form.component.scss']
})
export class ClientFormComponent implements OnInit {
    client: Client;

    constructor(
        public dialogRef: MatDialogRef<ClientFormComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private clientService: ClientService
    ) {}

    ngOnInit(): void {
        console.log('Pre Init:', this.data);
        
        if (this.data && this.data.id !== undefined) {
            this.client = new Client();
            this.client.id = this.data.id;
            this.client.name = this.data.name;
            // console.log('', this.client);
        } else if (this.data && this.data.client) {
            this.client = Object.assign(new Client(), this.data.client);
            // console.log('data.client:', this.client);
        } else {
            this.client = new Client();
            // console.log('creado:', this.client);
        }
    }

    onSave(): void {        
        if (this.client.id) {
            this.clientService.update(this.client.id, this.client).subscribe({
                next: () => {
                    this.dialogRef.close(true);
                },
                error: (error) => {
                    alert('El nombre del cliente ya existe');
                }
            });
        } else {
            this.clientService.create(this.client).subscribe({
                next: () => {
                    this.dialogRef.close(true);
                },
                error: (error) => {
                    alert('El nombre del cliente ya existe');
                }
            });
        }
    }

    onClose(): void {
        this.dialogRef.close();
    }
}