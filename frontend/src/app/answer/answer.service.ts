import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AnswerRequest } from './answer-request';
import { AnswerResponse } from './answer-response';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

    private url = "http://localhost:8080/api/answer";

    constructor(private http: HttpClient) { }

    getAllAnswersForQuestion(id: number): Observable<Array<AnswerResponse>> {
        return this.http.get<Array<AnswerResponse>>(`${this.url}/${id}`);
    }

    save(answerRequest: AnswerRequest) {
        return this.http.post(this.url, answerRequest);
    }

}