import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Question } from './question';
import { QuestionRequest } from './question-request';

@Injectable({
    providedIn: 'root'
})
export class QuestionService {

    constructor(private http: HttpClient) { }
    private url = "http://localhost:8080/api/question";

    getAllQuestions(): Observable<Array<Question>> {
        return this.http.get<Array<Question>>(this.url);
    }

    save(questionRequest: QuestionRequest) {
        return this.http.post(this.url, questionRequest);
    }

    getAllByTopic(id: number): Observable<Array<Question>> {
        return this.http.get<Array<Question>>(this.url + "/topic/" + id);
    }

}
