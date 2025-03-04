import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../../models/client/client.model';
import { API_ROUTES } from '../../constants/api.routes';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  constructor(private http: HttpClient) { }

  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(API_ROUTES.CLIENTS.GET_ALL);
  }

  get(id: number): Observable<Client> {
    return this.http.get<Client>(`${API_ROUTES.CLIENTS.GET_ALL}/${id}`);
  }

  create(client: Client): Observable<Client> {
    return this.http.post<Client>(API_ROUTES.CLIENTS.CREATE, client);
  }

  update(id: number, client: Client): Observable<Client> {
    return this.http.put<Client>(API_ROUTES.CLIENTS.UPDATE(id), client);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(API_ROUTES.CLIENTS.DELETE(id));
  }
}
