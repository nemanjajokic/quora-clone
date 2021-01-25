import { HttpClient } from '@angular/common/http';
import { Injectable, Output, EventEmitter } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from './sign-in/authentication-response';
import { LoginRequest } from './sign-in/login-request';
import { SignUpRequest } from './sign-in/sign-up-request';
import { map, tap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
    @Output() userName: EventEmitter<string> = new EventEmitter();
    private url = "http://localhost:8080/api/auth";

    refreshTokenRequest = {
        username: this.localStorage.retrieve("username"),
        token: this.localStorage.retrieve("refreshToken")
    }

    constructor(private http: HttpClient, private localStorage: LocalStorageService) { }

    signUp(signUpRequest: SignUpRequest): Observable<Object> {
        return this.http.post(`${this.url}/signup`, signUpRequest, { responseType: "text" });
    }

    login(loginRequest: LoginRequest) {
        return this.http.post<AuthenticationResponse>(`${this.url}/login`, loginRequest).pipe(map(data => {
            this.localStorage.store("jwtToken", data.jwtToken);
            this.localStorage.store("username", data.username);
            this.localStorage.store("refreshToken", data.refreshToken);
            this.localStorage.store("expiration", data.expiration);
            this.loggedIn.emit(true);
            this.userName.emit(data.username);
        }));
    }

    logout() {
        this.http.post(`${this.url}/logout`, this.refreshTokenRequest);
        this.localStorageClearAll();
        this.loggedIn.emit(false);
    }

    getJwt() {
        return this.localStorage.retrieve("jwtToken");
    }

    getUserName() {
        return this.localStorage.retrieve("username");
    }

    refreshToken() {
        return this.http.post<AuthenticationResponse>(`${this.url}/refresh/token`, this.refreshTokenRequest).pipe(
            tap((token) => {
                this.localStorage.clear("jwtToken");
                this.localStorage.clear("expiration");
                this.localStorage.store("jwtToken", token.jwtToken);
                this.localStorage.store("expiration", token.expiration);
            })
        )
    }

    localStorageClearAll() {
        this.localStorage.clear('jwtToken');
        this.localStorage.clear('username');
        this.localStorage.clear('refreshToken');
        this.localStorage.clear('expiration');
    }

    isLoggedIn() {
        return !!this.getJwt();
    }

}
