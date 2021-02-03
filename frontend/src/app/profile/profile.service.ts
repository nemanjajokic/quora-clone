import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserView } from './user-view';

export interface UserInfo {
    username?: string;
    email?: string;
    imageUri?: string;
}

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    private url = "http://localhost:8080/api/user/";
    userView = new BehaviorSubject<UserInfo>({});
    dataStore: { user: UserInfo } = { user: <UserInfo>{} };

    constructor(private http: HttpClient) { }

    updateUserInfo(username: string) {
        this.http.get(this.url + username).subscribe(data => {
            this.dataStore.user = data;
            this.userView.next(Object.assign({}, this.dataStore).user);
            console.log(data);
        });
    }

    getUserInfo(username: string): Observable<UserView> {
        return this.http.get<UserView>(this.url + username);
    }

}