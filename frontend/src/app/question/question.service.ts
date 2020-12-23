import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Question } from './question';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) { }

  getAllQuestions(): Observable<Array<Question>> {
    return this.http.get<Array<Question>>("http://localhost:8080/api/question");
  }

}
