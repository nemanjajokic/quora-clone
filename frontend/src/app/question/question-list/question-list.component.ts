import { Component, OnInit } from '@angular/core';
import { AnswerResponse } from 'src/app/answer/answer-response';
import { AnswerService } from 'src/app/answer/answer.service';
import { Question } from '../question';
import { QuestionService } from '../question.service';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {

  questions: Array<Question> = [];  // let answer of question.answers
  isCollapsed: boolean[] = [];
  answers: Array<AnswerResponse> = []; // make 2D array or whatever to be specific for every question

  constructor(private questionService: QuestionService, private answerService: AnswerService) {
    this.questionService.getAllQuestions().subscribe(data => {
      this.questions = data;
    });
  }

  ngOnInit(): void {
  }

  getAnswers(id: number) {
    this.answerService.getAllAnswersForQuestion(id).subscribe(data => {
      this.answers = data;
    });
  }

}
