import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: 'categories', loadComponent: () => import('../app/shared/components/category/category-list/category-list.component').then(m => m.CategoryListComponent)},
    
    { path: 'clients', 
        loadComponent: () => 
            import(
                '../app/shared/components/clients/client-list/client-list.component'
            ).then(m => m.ClientListComponent)
    },
];