import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from './topic';

@Injectable({
    providedIn: 'root'
})
export class TopicService {

    private url = "http://localhost:8080/api/topic";

    constructor(private http: HttpClient) { }

    getAllTopics(): Observable<Array<Topic>> {
        return this.http.get<Array<Topic>>(this.url);
    }

    getTopic(id: number): Observable<Topic> {
        return this.http.get<Topic>(this.url + "/" + id);
    }

    saveTopic(topic: Topic) {
        return this.http.post(this.url, topic);
    }

}
