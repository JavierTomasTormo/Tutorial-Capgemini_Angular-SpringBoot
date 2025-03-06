import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoanListComponent } from '../../shared/components/loans/loan-list/loan-list.component';
import { LoanCreateComponent } from '../../shared/components/loans/loan-create/loan-create.component';

const routes: Routes = [
  { path: '', component: LoanListComponent },
  { path: 'create', component: LoanCreateComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoansRoutingModule { }