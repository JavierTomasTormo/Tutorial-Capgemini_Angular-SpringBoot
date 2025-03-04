import { Component, Inject, HostListener } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-dialog-confirmation',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './dialog-confirmation.component.html',
    styleUrl: './dialog-confirmation.component.scss',
})
export class DialogConfirmationComponent {
    title: string;
    description: string;

    constructor(
        public dialogRef: MatDialogRef<DialogConfirmationComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {}

    ngOnInit(): void {
        this.title = this.data.title;
        this.description = this.data.description;
        
        document.body.classList.add('dialog-open');
    }
    
    ngOnDestroy(): void {
        document.body.classList.remove('dialog-open');
    }

    onClose(value = false): void {
        this.dialogRef.close(value);
    }
    
    onBackdropClick(event: MouseEvent): void {
        if ((event.target as HTMLElement).className === 'dialog-overlay') {
            this.onClose();
        }
    }
    
    @HostListener('document:keydown.escape')
    onEscapePress(): void {
        this.onClose();
    }
}