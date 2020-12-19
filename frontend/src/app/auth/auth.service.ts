import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from './sign-in/authentication-response';
import { LoginRequest } from './sign-in/login-request';
import { SignUpRequest } from './sign-in/sign-up-request';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = "http://localhost:8080/api/auth";

  constructor(private http: HttpClient, private localStorage: LocalStorageService) { }

  signUp(signUpRequest: SignUpRequest): Observable<Object> {
    return this.http.post(`${this.url}/signup`, signUpRequest, {responseType: "text"});
  }

  login(loginRequest: LoginRequest) {
    return this.http.post<AuthenticationResponse>(`${this.url}/login`, loginRequest).pipe(map(data => {
      this.localStorage.store("jwtToken", data.jwtToken);
      this.localStorage.store("username", data.username);
      this.localStorage.store("refreshToken", data.refreshToken);
      this.localStorage.store("expiration", data.expiration);
    }));
  }

}
