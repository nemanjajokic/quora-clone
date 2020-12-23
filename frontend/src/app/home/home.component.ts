import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Question } from '../question/question';
import { QuestionService } from '../question/question.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  questions: Array<Question> = [];

  constructor(private service: QuestionService) {
    this.service.getAllQuestions().subscribe(data => {
      this.questions = data;
    })
  }

  ngOnInit(): void {
  }

}
