import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoanListComponent } from '../../shared/components/loans/loan-list/loan-list.component';

const routes: Routes = [
    { path: '', component: LoanListComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoansRoutingModule { }