import { HttpClient } from '@angular/common/http';
import { Injectable, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import { UserView } from './user-view';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  @Output() userResponse: EventEmitter<UserView> = new EventEmitter();
  @Output() userImageUri: EventEmitter<string> = new EventEmitter();

  constructor(private http: HttpClient) { }

  getUserInfo(username: string): Observable<UserView> {
    return this.http.get<UserView>("http://localhost:8080/api/user/" + username);
  }

}
