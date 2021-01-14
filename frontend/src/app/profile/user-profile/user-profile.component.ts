import { Component, OnInit } from '@angular/core';
import { ImageService } from '../image.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private service: ImageService) { }

  public formData = new FormData();
  public selectedFile: File = null;

  ngOnInit(): void {
  }

  onSelectFile(event) {
    this.selectedFile = event.target.files[event.target.files.length - 1] as File;

    this.formData.set("file", this.selectedFile, this.selectedFile.name);
    this.service.uploadImage(this.formData).subscribe(res => {
      console.log(res);
    });
  }

  performUpload() {
    this.formData.set("file", this.selectedFile, this.selectedFile.name);
    this.service.uploadImage(this.formData).subscribe(res => {
      console.log(res);
    });
  }

}
