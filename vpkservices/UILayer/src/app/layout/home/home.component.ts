import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DataShareService } from 'src/app/core/services/data-share.service';
import { LoaderService } from 'src/app/core/services/loader.service';
import { ToastService } from 'src/app/core/services/toast-service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private modalService: NgbModal, public toastService: ToastService,private loaderService: LoaderService,private dataService: DataShareService) { }

  ngOnInit(): void {
    this.toastService.show('I am a success toast', { classname: 'bg-success text-light', delay: 5000 });
  }

}
