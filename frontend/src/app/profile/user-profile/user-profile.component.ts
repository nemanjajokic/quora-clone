import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { ImageService } from '../image.service';
import { UserView } from '../user-view';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  username: string;
  userResponse: UserView = {} as UserView;

  constructor(private service: ImageService, private userService: UserService, private authService: AuthService) { }

  public formData = new FormData();
  public selectedFile: File = null;

  ngOnInit(): void {
    this.username = this.authService.getUserName();
  //  this.username = this.route.snapshot.params["username"];
    this.userService.getUserInfo(this.username).subscribe(data => {
      this.userResponse = data;
    });
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
