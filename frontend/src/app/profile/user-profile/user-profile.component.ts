import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { ImageService } from '../image.service';
import { ProfileService } from '../profile.service';
import { UserView } from '../user-view';

@Component({
    selector: 'app-user-profile',
    templateUrl: './user-profile.component.html',
    styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

    constructor(private service: ImageService, private profileService: ProfileService, private authService: AuthService) { }

    username: string;
    public formData = new FormData();
    public selectedFile: File = null;
    user: UserView = <UserView>{};

    ngOnInit(): void {
        this.authService.userName.subscribe((data: string) => this.username = data);
        this.username = this.authService.getUserName();
        this.profileService.getUserInfo(this.username).subscribe(data => this.user = data);
    }

    onSelectFile(event) {
        this.selectedFile = event.target.files[event.target.files.length - 1] as File;

        this.formData.set("file", this.selectedFile, this.selectedFile.name);
        this.service.uploadImage(this.formData).subscribe(res => {
            console.log(res);
            this.profileService.updateUserInfo(this.username);
            this.refresh();
        });
    }

    refresh() {
        this.profileService.getUserInfo(this.username).subscribe(data => {
            this.user = data;
            console.log(data.imageUri);
        });
    }

    performUpload() {
        this.formData.set("file", this.selectedFile, this.selectedFile.name);
        this.service.uploadImage(this.formData).subscribe(() => this.refresh());
    }

}
