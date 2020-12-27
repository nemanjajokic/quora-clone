import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from './topic';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) { }

  getAllTopics(): Observable<Array<Topic>> {
    return this.http.get<Array<Topic>>("http://localhost:8080/api/topic");
  }

  saveTopic(topic: Topic) {
    return this.http.post("http://localhost:8080/api/topic", topic);
  }

}
