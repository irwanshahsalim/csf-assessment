import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {
  bundles: any = [];

  constructor(private http: HttpClient, private uploadService: UploadService) { }

  ngOnInit(): void {
    this.getBundles();
  }

  getBundles(): void {
    this.uploadService.getBundles().subscribe(
      data => {
        this.bundles = data;
      },
      error => {
        console.error('Error:', error);
      }
    );
  }

  onBundleClick(bundleId: string): void {
    // Code to navigate to View2 goes here
    // You might use Angular router's navigate method
  }
}
