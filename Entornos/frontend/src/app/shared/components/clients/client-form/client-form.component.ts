import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClientService } from '../../../../core/services/client/client.service';
import { Client } from '../../../../core/models/client/client.model';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-client-form',
    standalone: true,
    imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
    templateUrl: './client-form.component.html',
    styleUrls: ['./client-form.component.scss']
})
export class ClientFormComponent implements OnInit {
    client: Client;

    constructor(
        public dialogRef: MatDialogRef<ClientFormComponent>,
        @Inject(MAT_DIALOG_DATA) public data: { client: Client },
        private clientService: ClientService
    ) {}

    ngOnInit(): void {
        this.client = this.data.client ? Object.assign({}, this.data.client) : new Client();
    }

    onSave(): void {
        if (this.client.id) {
            this.clientService.update(this.client.id, this.client).subscribe(() => {
                this.dialogRef.close(true);
            });
        } else {
            this.clientService.create(this.client).subscribe(() => {
                this.dialogRef.close(true);
            });
        }
    }

    onClose(): void {
        this.dialogRef.close();
    }
}