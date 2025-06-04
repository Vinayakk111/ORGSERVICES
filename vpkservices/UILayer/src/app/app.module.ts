import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './layout/sidebar/sidebar.component';
import { HeaderComponent } from './layout/header/header.component';
import { FooterComponent } from './layout/footer/footer.component';
import { AboutComponentComponent } from './layout/about-component/about-component.component';
import { HomeComponent } from './layout/home/home.component';
import { SliderComponent } from './layout/slider/slider.component';
import { ToastsComponent } from './layout/toasts/toasts.component';
import { LoaderComponent } from './layout/loader/loader.component';
import { HttpClientModule } from '@angular/common/http';
import { SignupComponent } from './layout/signup/signup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MenubarModule } from 'primeng/menubar';
import { SidebarModule } from 'primeng/sidebar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ChartModule } from 'primeng/chart';
import { CardModule } from 'primeng/card';
import { AccordionModule, AutoCompleteModule, DialogModule, InputSwitchModule, ListboxModule, OverlayPanelModule, ProgressBarModule, ToastModule } from 'primeng';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { CountryService } from './core/services/countryservice';


@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    HeaderComponent,
    FooterComponent,
    AboutComponentComponent,
    HomeComponent,
    SliderComponent,
    ToastsComponent,
    LoaderComponent,
    SignupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AccordionModule,
    DialogModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MenubarModule,
    SidebarModule,
    BrowserAnimationsModule,
    ButtonModule,
    ToastModule,
    CardModule,
    InputTextModule,
    ScrollingModule,
    ChartModule,
    ListboxModule,
    ProgressBarModule,
    InputTextModule,
    OverlayPanelModule,
    AutoCompleteModule,
    InputSwitchModule 
  ],
  providers: [CountryService],
  bootstrap: [AppComponent]
})
export class AppModule { }
