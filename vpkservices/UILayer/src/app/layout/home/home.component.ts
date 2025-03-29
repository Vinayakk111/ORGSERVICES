import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { map } from 'rxjs/operators';
import { ApiService } from 'src/app/core/services/api.service';
import { DataShareService } from 'src/app/core/services/data-share.service';
import { LoaderService } from 'src/app/core/services/loader.service';
import { ToastService } from 'src/app/core/services/toast-service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  cities: any[] = [];
  data: any;
  jsonString:any;
  constructor(private apiService: ApiService, private modalService: NgbModal, public toastService: ToastService, private loaderService: LoaderService, private dataService: DataShareService) { }
  ngOnInit(): void {
    this.toastService.show('I am a success toast', { classname: 'bg-success text-light', delay: 5000 });
  }
  getcityData() {
    this.apiService.getCities().subscribe(
      (data) => {
        this.cities = data;  // Store API response in variable
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }
  getcityDataGen() {
    this.apiService.get('getcnlang1').pipe(
      map((response: any) => response.map((city: any) => ({
        language: city.language,
        percentage: city.percentage
      })))
    ).subscribe({
      next: (cities:any[]) => {
        console.log(/* JSON.stringify */(cities));
        this.jsonString = JSON.stringify(this.cities, null, 2); // ✅ Format JSON
        this.cities  = cities;
        this.cities=JSON.parse(JSON.stringify(this.cities));
        console.log('API Response:', this.cities);
      },
      error: (error) => {
        console.error('getcnlang1 API Error:', error);
      }
    });
  }
}
