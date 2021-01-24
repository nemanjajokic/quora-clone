import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/profile/profile.service';
import { AuthService } from '../auth.service';
import { LoginRequest } from './login-request';
import { SignUpRequest } from './sign-up-request';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  signUpRequest: SignUpRequest;
  loginRequest: LoginRequest;
  signUpForm: FormGroup;
  loginForm: FormGroup;
  userResponse: UserView;

  constructor(private service: AuthService, private profileService: ProfileService, private router: Router) {
    this.signUpRequest = {
      username: "",
      password: "",
      email: ""
    }
    this.loginRequest = {
      username: "",
      password: ""
    }
  }

  ngOnInit(): void {
    this.signUpForm = new FormGroup({
      username: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required),
      email: new FormControl("", Validators.required)
    });
    this.loginForm = new FormGroup({
      username: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required)
    });
  }

  redirectToHome() {
    this.router.navigateByUrl("");
  }

  redirectToSignIn() {
    this.router.navigate(["/sign-in"]);
  }

  signUp() {
    this.signUpRequest.username = this.signUpForm.get("username").value;
    this.signUpRequest.password = this.signUpForm.get("password").value;
    this.signUpRequest.email = this.signUpForm.get("email").value;

    this.authService.signUp(this.signUpRequest).subscribe((data) => {
      console.log(data);
      this.signUpForm.reset();
    });
  }

  login() {
    this.loginRequest.username = this.loginForm.get("username").value;
    this.loginRequest.password = this.loginForm.get("password").value;

    this.authService.login(this.loginRequest).subscribe((data) => {
      console.log(data);

      this.profileService.updateUserInfo(this.loginRequest.username);
      this.profileService.metoda();
      
      this.redirectToHome();
/*
      this.userService.getUserInfo(this.loginRequest.username).subscribe((res) => {
        console.log(res);
        this.userResponse = res;
        this.redirectToHome();
      });
*/      
    });
/*
    this.userService.getUserInfo(this.loginRequest.username).subscribe((res) => {
      console.log(res);
      this.userResponse = res;
    });
*/
  }

}
