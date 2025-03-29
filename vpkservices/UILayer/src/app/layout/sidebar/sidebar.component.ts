import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  isCollapsed = true; // State of sidebar

  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
  }

}
