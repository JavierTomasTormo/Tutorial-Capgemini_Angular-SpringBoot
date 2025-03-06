import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_ROUTES } from '../../constants/api.routes';
import { Loan } from '../../models/loan/loan.model';
import { LoanSearchDto } from '../../models/loan/loanSearch.model';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private apiUrl = API_ROUTES.LOANS;

  constructor(private http: HttpClient) { }

  getAll(): Observable<Loan[]> {
    return this.http.post<Loan[]>(this.apiUrl.SEARCH, {});
  }

  getOne(id: number): Observable<Loan> {
    return this.http.get<Loan>(this.apiUrl.GET_ONE(id));
  }

  search(criteria: LoanSearchDto): Observable<{ content: Loan[], totalElements: number }> {
    let params = new HttpParams();
    Object.keys(criteria).forEach(key => {
      if (criteria[key] !== null && criteria[key] !== undefined) {
        params = params.set(key, criteria[key]);
      }
    });

    return this.http.post<{ content: Loan[], totalElements: number }>(this.apiUrl.SEARCH, criteria, { params });
  }

  save(loan: Loan): Observable<Loan> {
    return this.http.post<Loan>(this.apiUrl.CREATE, loan);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(this.apiUrl.DELETE(id));
  }
}