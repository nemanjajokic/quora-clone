import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { ProfileService } from '../profile/profile.service';
import { UserView } from '../profile/user-view';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    isLoggedIn: boolean;
    username: string;
    userView: any;

    get imageUri() { return (this.userView && this.userView.imageUri) ? this.userView.imageUri : null }

    constructor(private authService: AuthService, private profileService: ProfileService, private router: Router) { }

    ngOnInit(): void {
        this.authService.loggedIn.subscribe((data: boolean) => this.isLoggedIn = data);
        this.authService.userName.subscribe((data: string) => this.username = data);
        this.isLoggedIn = this.authService.isLoggedIn();
        this.username = this.authService.getUserName();

        this.profileService.userView.subscribe((data: UserView) => this.userView = data);
        this.profileService.getUserInfo(this.username).subscribe(data => {
            this.userView = data;
            console.log(data);
        });
    }

    logout() {
        this.authService.logout();
        this.router.navigateByUrl("");
    }

}