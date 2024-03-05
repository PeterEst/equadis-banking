import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../models/utils/ApiResponse';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private baseUrl = `${environment.apiBaseEndpoint}/${environment.apiVersion}`;

  constructor(private http: HttpClient) {}

  get<T>(url: string, params?: HttpParams, headers?: HttpHeaders) {
    const options = { params, headers };
    return this.http.get<ApiResponse<T>>(`${this.baseUrl}/${url}`, options);
  }

  post<T>(url: string, body: any, headers?: HttpHeaders) {
    const options = { headers };
    return this.http.post<ApiResponse<T>>(
      `${this.baseUrl}/${url}`,
      body,
      options
    );
  }

  put<T>(url: string, body: any, headers?: HttpHeaders) {
    const options = { headers };
    return this.http.put<ApiResponse<T>>(
      `${this.baseUrl}/${url}`,
      body,
      options
    );
  }

  delete<T>(url: string, headers?: HttpHeaders) {
    const options = { headers };
    return this.http.delete<ApiResponse<T>>(`${this.baseUrl}/${url}`, options);
  }

  patch<T>(url: string, body: any, headers?: HttpHeaders) {
    const options = { headers };
    return this.http.patch<ApiResponse<T>>(
      `${this.baseUrl}/${url}`,
      body,
      options
    );
  }
}
