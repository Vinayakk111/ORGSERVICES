import { Component, OnInit, TemplateRef } from '@angular/core';
import { ToastService } from 'src/app/core/services/toast-service';

@Component({
  selector: 'app-toasts',
  template: `
    
  `,
  host: {}
})
export class ToastsComponent implements OnInit {

  constructor(public toastService: ToastService) {}

  isTemplate(toast) { return toast.textOrTpl instanceof TemplateRef; }

  ngOnInit(): void {
  }

}
