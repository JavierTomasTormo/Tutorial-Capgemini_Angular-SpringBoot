import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from '../../../../core/services/category/category.service';
import { Category } from '../../../../core/models/category/category.model';

@Component({
    selector: 'app-category-edit',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './category-edit.component.html',
    styleUrl: './category-edit.component.scss'
})
export class CategoryEditComponent implements OnInit {
    category: Category;
    duplicateNameError = false;

    constructor(
        public dialogRef: MatDialogRef<CategoryEditComponent>,
        @Inject(MAT_DIALOG_DATA) public data: {category?: Category},
        private categoryService: CategoryService
    ) {}

    ngOnInit(): void {
        document.body.classList.add('modal-open');
        
        this.category = this.data.category ? {...this.data.category} : new Category();
    }

    ngOnDestroy(): void {
        document.body.classList.remove('modal-open');
    }

    checkDuplicateName(): void {
        if (!this.category.name || this.category.name.trim() === '') {
            this.duplicateNameError = false;
            return;
        }

        this.categoryService.checkDuplicateName(this.category.name, this.category.id)
            .subscribe({
                next: (exists) => {
                    this.duplicateNameError = exists;
                },
                error: (err) => {
                    console.error('Error al verificar nombre duplicado:', err);
                    this.duplicateNameError = false;
                }
            });
    }

    onSave(): void {
        if (this.duplicateNameError) {
            return;
        }
        
        this.categoryService.checkDuplicateName(this.category.name, this.category.id)
            .subscribe({
                next: (exists) => {
                    if (exists) {
                        this.duplicateNameError = true;
                    } else {
                        this.categoryService.saveCategory(this.category).subscribe({
                            next: () => {
                                this.dialogRef.close(true);
                            },
                            error: (err) => {
                                console.error('Error al guardar la categoría:', err);
                            }
                        });
                    }
                },
                error: (err) => {
                    console.error('Error al verificar nombre duplicado:', err);
                }
            });
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