import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.css']
})
export class SliderComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

  }
  isCollapsed = false; // State of sidebar

  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
  }

}
