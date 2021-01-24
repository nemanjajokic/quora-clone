import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserView } from './user-view';
import { map, tap } from 'rxjs/operators';

export interface UserInfo {
  username?: string;
  email?: string;
  imageUri?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  userView = new BehaviorSubject<UserInfo>({});
  dataStore: {user: UserInfo} = {user: <UserInfo>{}};
  readonly user = this.userView.asObservable();

  constructor(private http: HttpClient) { }

  getUserView() {
    return this.userView.asObservable();
  }

  updateUserInfo(username: string) {
    this.http.get("http://localhost:8080/api/user/" + username).subscribe(data => {
      this.dataStore.user = data;
      this.userView.next(Object.assign({}, this.dataStore).user);
      console.log("updateUserInfo() called");
    });
  }

  metoda() {
    console.log("metoda pozvana");
  }

  getUserInfo(username: string): Observable<UserView> {
    return this.http.get<UserView>("http://localhost:8080/api/user/" + username);
  }

}
