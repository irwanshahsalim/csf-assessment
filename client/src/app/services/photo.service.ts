import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Upload } from '../models';
import {firstValueFrom} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {

  private apiUrl = "http://localhost:8080/upload";

  constructor(private http: HttpClient) { }


  postUpload(upload: Upload) {

    const formData = new FormData();
    formData.append('name', upload.name);
    formData.append('title', upload.title);
    formData.append('comments', upload.comments);
    formData.append('file', upload.archive);

    firstValueFrom(
          this.http.post(this.apiUrl, formData)
        )
          .then(() => {
            console.log('File uploaded successfully!');
            // Handle success
          })
          .catch((error) => {
            console.error('Error uploading file:', error);
            // Handle error
          });


  }
}





// upload(): void {
//   const formData = new FormData();
//   formData.append('name', this.name);
//   formData.append('title', this.title);
//   formData.append('comments', this.comments);
//   formData.append('archive', this.archive);

//   firstValueFrom(
//     this.http.post('http://localhost:8080/upload', formData)
//   )
//     .then(() => {
//       console.log('File uploaded successfully!');
//       // Handle success
//     })
//     .catch((error) => {
//       console.error('Error uploading file:', error);
//       // Handle error
//     });
// }
// }
