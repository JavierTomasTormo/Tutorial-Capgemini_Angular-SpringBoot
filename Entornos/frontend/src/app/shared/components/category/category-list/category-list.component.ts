import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryService } from '../../../../core/services/category/category.service';
import { Category } from '../../../../core/models/category/category.model';
import { CategoryEditComponent } from '../category-edit/category-edit.component';
import { DialogConfirmationComponent } from '../../../../core/dialogs/dialog-confirmation/dialog-confirmation.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-category-list',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './category-list.component.html',
    styleUrl: './category-list.component.scss'
})
export class CategoryListComponent implements OnInit {
    categories: Category[] = [];

    constructor(
        private categoryService: CategoryService,
        public dialog: MatDialog
    ) { }

    ngOnInit(): void {
        this.loadCategories();
    }

    loadCategories(): void {
        this.categoryService.getCategories().subscribe(data => {
            this.categories = data;
        });
    }

    createCategory(): void {
        const dialogRef = this.dialog.open(CategoryEditComponent, {
            width: '450px',
            panelClass: 'custom-dialog',
            backdropClass: 'custom-backdrop',
            disableClose: true,
            autoFocus: false,
            data: {}
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.loadCategories();
            }
        });
    }

    editCategory(category: Category): void {
        const dialogRef = this.dialog.open(CategoryEditComponent, {
            width: '450px',
            panelClass: 'custom-dialog',
            backdropClass: 'custom-backdrop',
            disableClose: true,
            autoFocus: false,
            data: { category: category }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.loadCategories();
            }
        });
    }

    deleteCategory(category: Category): void {
        const dialogRef = this.dialog.open(DialogConfirmationComponent, {
            panelClass: 'custom-dialog',
            data: { title: "Eliminar categoría", description: "Atención si borra la categoría se perderán sus datos.<br> ¿Desea eliminar la categoría?" }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.categoryService.deleteCategory(category.id).subscribe(() => {
                    this.loadCategories();
                });
            }
        });
    }
}