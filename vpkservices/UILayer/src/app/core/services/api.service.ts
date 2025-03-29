import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { catchError, retry } from 'rxjs/operators';


@Injectable({
  providedIn: 'root' // This makes it available across the app
})
export class ApiService {

  private getCitiesapiUrl =  environment.apiUrl+'/getcnlang1'; // Example API
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  get<T>(endpoint: string): Observable<T> {
    // return this.http.get<T>(`${this.apiUrl}/${endpoint}`);
    return this.http.get<T>(`${this.apiUrl}/${endpoint}`).pipe(
      // retry(3), // Retry failed requests up to 3 times
      catchError((error) => {
        console.error(endpoint+' API Error:', error);
        return throwError(() => new Error(error.message));
      })
    );
  }

  // Generic POST method
  post<T>(endpoint: string, data: any): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, data).pipe(
      // retry(3), // Retry failed requests up to 3 times
      catchError((error) => {
        console.error(endpoint+' API Error:', error);
        return throwError(() => new Error(error.message));
      })
    );
  }

  // Generic PUT method
  put<T>(endpoint: string, data: any): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${endpoint}`, data).pipe(
      // retry(3), // Retry failed requests up to 3 times
      catchError((error) => {
        console.error(endpoint+' API Error:', error);
        return throwError(() => new Error(error.message));
      })
    );
  }

  // Generic DELETE method
  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.apiUrl}/${endpoint}`).pipe(
      // retry(3), // Retry failed requests up to 3 times
      catchError((error) => {
        console.error(endpoint+' API Error:', error);
        return throwError(() => new Error(error.message));
      })
    );
  }

  // GET request to fetch data
  getCities(): Observable<any> {
    return this.http.get(this.getCitiesapiUrl);
  }
}
