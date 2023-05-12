import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { PhotoService } from '../services/photo.service';
import { Upload } from '../models';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit {  // Corrected the class name here
  @ViewChild('imageFile') imageFile!: any;

  searchForm!: FormGroup;
  name!: string;
  title!: string;
  comments!: string;
  archive!: File;
  isCancelled: boolean = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient,
    private uploadSvc : PhotoService
  ) {}

  ngOnInit(): void {
    this.searchForm = this.createForm();
  }

  private createForm(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required],
      title: ['', Validators.required],
      comments: [''],
      archive: [null, Validators.required]
    });
  }

  onSubmit(): void {
    if (this.isCancelled) {
      console.log('Form submission cancelled!');
      return;
    }

    this.name = this.searchForm.value['name'];
    this.title = this.searchForm.value['title'];
    this.comments = this.searchForm.value['comments'];
    this.archive = this.searchForm.value['archive'];
    console.log('Form submitted!');
    console.log('Name:', this.name);
    console.log('Title:', this.title);
    console.log('Comments:', this.comments);
    console.log('Archive:', this.archive);

    const upload: Upload = {
      name: this.name,
      title: this.title,
      comments: this.comments,
      archive: this.archive
    };

    this.uploadSvc.postUpload(upload);
  }

  onFileSelected(event: any): void {
    const files = event.target.files;
    if (files && files.length > 0) {
      this.archive = files[0];
      this.searchForm.patchValue({ archive: this.archive });
    }
  }

  onCancel(): void {
    console.log('Form cancelled!');
    this.isCancelled = true;
    // Add your code to handle cancellation
  }

}