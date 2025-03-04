import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Category } from '../../models/category/category.model';
import { CATEGORY_DATA } from '../../models/mock/mock-categories';
import { HttpClient } from '@angular/common/http';
import { API_ROUTES } from '../../constants/api.routes';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  constructor(
    private http: HttpClient
  ) { }

  getCategories(): Observable<Category[]> {
    return of(CATEGORY_DATA);
    // return this.http.get<Category[]>(API_ROUTES.CATEGORIES.GET_ALL);
  }

  saveCategory(category: Category): Observable<Category> {
    const { id } = category;
    const url = id ? API_ROUTES.CATEGORIES.UPDATE(id) : API_ROUTES.CATEGORIES.CREATE;
    return this.http.put<Category>(url, category);
  }

  deleteCategory(idCategory: number): Observable<any> {
    return this.http.delete(API_ROUTES.CATEGORIES.DELETE(idCategory));
  }


  checkDuplicateName(name: string, excludeId?: number): Observable<boolean> {
    return this.getCategories().pipe(
      map(categories => {
        return categories.some(category => 
          category.name.toLowerCase() === name.toLowerCase() && 
          category.id !== excludeId
        );
      })
    );
  }
}
