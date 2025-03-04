import { Routes } from '@angular/router';
import { ErrorComponent } from './shared/components/error/error.component';
import { Error500Component } from './shared/components/error500/error500.component';

export const routes: Routes = [
  { 
    path: '', 
    redirectTo: 'home', 
    pathMatch: 'full' 
  },
  {
    path: 'home',
    loadComponent: () => import('./shared/components/home/home.component').then(c => c.HomeComponent)
  },
  {
    path: 'categories',
    loadChildren: () => import('./modules/categories/categories.module').then(m => m.CategoriesModule)
  },
  {
    path: 'clients',
    loadChildren: () => import('./modules/client/client.module').then(m => m.ClientModule)
  },
  {
    path: 'loans',
    loadChildren: () => import('./modules/loans/loans.module').then(m => m.LoansModule)
  },
  { 
    path: '404', 
    component: ErrorComponent 
  },
  { 
    path: '500', 
    component: Error500Component 
  },
  { 
    path: '**', 
    redirectTo: '404' 
  },
];