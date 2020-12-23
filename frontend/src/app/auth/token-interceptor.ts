import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { filter, switchMap, take, catchError } from "rxjs/operators";
import { AuthService } from "./auth.service";
import { AuthenticationResponse } from "./sign-in/authentication-response";

@Injectable({
    providedIn: 'root'
})
export class TokenInterceptor implements HttpInterceptor {
    
    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    constructor(public authService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (request.url.indexOf('refresh') !== -1 || request.url.indexOf('login') !== -1) {
            return next.handle(request);
        }

        if (this.authService.getJwt()) {
            return next.handle(this.addToken(request, this.authService.getJwt())).pipe(catchError(error => {
                if (error instanceof HttpErrorResponse && error.status === 403) {
                    return this.handleAuthErrors(request, next);
                } else {
                    return throwError(error);
                }
            }));
        }

        return next.handle(request);
    }

    private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (!this.isRefreshing) {
            this.isRefreshing = true;
            this.refreshTokenSubject.next(null);

            return this.authService.refreshToken().pipe(
                switchMap((refreshTokenResponse: AuthenticationResponse) => {
                    this.isRefreshing = false;
                    this.refreshTokenSubject.next(refreshTokenResponse.jwtToken);

                    return next.handle(this.addToken(req, refreshTokenResponse.jwtToken));
                })
            )
        } else {
            return this.refreshTokenSubject.pipe(
                filter(token => token !== null),
                take(1),
                switchMap((data) => {
                    return next.handle(this.addToken(req, this.authService.getJwt()))
                })
            );
        }
    }

    private addToken(request: HttpRequest<any>, jwt: string) {
        return request.clone({
            setHeaders: { 'Authorization': `Bearer ${jwt}` }
        });
    }

}