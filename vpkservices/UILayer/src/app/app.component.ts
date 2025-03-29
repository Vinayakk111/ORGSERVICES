import { Component } from '@angular/core';
import { environment } from 'src/environments/environment';
import { DataShareService } from './core/services/data-share.service';
import { LoaderService } from './core/services/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'UILayer' + environment.apiUrl;
  isLoading = false;
  showSpinner: string = "N";
  isToastVisible = false;



  constructor(private dataService: DataShareService, private loaderService: LoaderService) { }

  ngOnInit() {
    this.dataService.currentMessage.subscribe(message => {
      this.showSpinner = message;
      this.isLoading = this.showSpinner === 'Y' ? true : false;
      this.executeAfterDelay();
    });
  }

  executeAfterDelay() {
    // console.log('Waiting for 3 seconds...');
    setTimeout(() => {
      this.isLoading = false;
      // console.log('Executed after 3 seconds!');
    }, 3000); // 3000ms = 3 seconds
  }

  showToast() {
    this.isToastVisible = true;

    // Auto-hide after 3 seconds (optional)
    setTimeout(() => {
      this.isToastVisible = false;
    }, 3000);
  }
}
