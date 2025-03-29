import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataShareService {

  private messageSource = new BehaviorSubject<string>('N'); // Holds data
  currentMessage = this.messageSource.asObservable(); // Observable for updates

  showSpinner(message: string) {
    this.messageSource.next(message); // Update data
  }
}
