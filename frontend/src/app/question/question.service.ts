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

  getAllQuestions(): Observable<Array<Question>> {
    return this.http.get<Array<Question>>("http://localhost:8080/api/question");
  }

  save(questionRequest: QuestionRequest) {
    return this.http.post("http://localhost:8080/api/question", questionRequest);
  }

  getAll(): Observable<Array<Question>> {
    return this.http.get<Array<Question>>("http://localhost:8080/api/question/all");
  }
  
  getAllByTopic(id: number): Observable<Array<Question>> {
    return this.http.get<Array<Question>>("http://localhost:8080/api/question/all/" + id);
  }

}
