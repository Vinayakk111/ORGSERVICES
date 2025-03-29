import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root' // This makes it available across the app
})
export class ApiService {

  private getCitiesapiUrl =  environment.apiUrl+'/getcnlang1'; // Example API

  constructor(private http: HttpClient) { }

  // GET request to fetch data
  getCities(): Observable<any> {
    return this.http.get(this.getCitiesapiUrl);
  }
}
