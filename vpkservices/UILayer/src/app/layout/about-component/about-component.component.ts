import { Component, EventEmitter, OnInit, Output } from '@angular/core';

import { DataShareService } from 'src/app/core/services/data-share.service';
import { LoaderService } from 'src/app/core/services/loader.service';
import { ToastService } from 'src/app/core/services/toast-service';


interface Country {
  name: string;
  flag: string;
  area: number;
  population: number;
}

const COUNTRIES: Country[] = [
  {
    name: 'Russia',
    flag: 'f/f3/Flag_of_Russia.svg',
    area: 17075200,
    population: 146989754
  },
  {
    name: 'Canada',
    flag: 'c/cf/Flag_of_Canada.svg',
    area: 9976140,
    population: 36624199
  },
  {
    name: 'United States',
    flag: 'a/a4/Flag_of_the_United_States.svg',
    area: 9629091,
    population: 324459463
  },
  {
    name: 'China',
    flag: 'f/fa/Flag_of_the_People%27s_Republic_of_China.svg',
    area: 9596960,
    population: 1409517397
  }
];


@Component({
  selector: 'app-about-component',
  templateUrl: './about-component.component.html',
  styles: [`
    .star {
      font-size: 1.5rem;
      color: #b0c4de;
    }
    .filled {
      color: #1e90ff;
    }
    .bad {
      color: #deb0b0;
    }
    .filled.bad {
      color: #ff1e1e;
    }
  `]
})
export class AboutComponentComponent implements OnInit {

  ngOnInit(): void {
  }

  currentRate = 6;
  closeResult = '';
  

  constructor( public toastService: ToastService,private loaderService: LoaderService,private dataService: DataShareService) { }
  showStandard() {
    this.toastService.show('I am a standard toast');
  }

  showSuccess() {
    this.toastService.show('I am a success toast', { classname: 'bg-success text-light', delay: 5000 });
  }

  showDanger(dangerTpl) {
    this.toastService.show(dangerTpl, { classname: 'bg-danger text-light', delay: 5000 });
  }
  open(content) {
    
  }

  private getDismissReason(reason: any): string {
   return '';
  }

  countries = COUNTRIES;
  images = [944, 1011, 984].map((n) => `https://picsum.photos/id/${n}/900/500`);

  showSpinner() {
    console.log('Hiii');
    this.dataService.showSpinner("Y"); //to show spinner
  }
  isToastVisible = false;

  showToast() {
    this.isToastVisible = true;

    // Auto-hide after 3 seconds (optional)
    setTimeout(() => {
      this.isToastVisible = false;
    }, 3000);
  }
}
