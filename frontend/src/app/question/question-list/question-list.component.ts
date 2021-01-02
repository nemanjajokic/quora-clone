import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AnswerRequest } from 'src/app/answer/answer-request';
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

  questions: Array<Question> = [];
  isCollapsed: boolean[] = [];
  questionId: number = 0;

  answerForm: FormGroup;
  answerRequest: AnswerRequest;

  constructor(private questionService: QuestionService, private answerService: AnswerService) {
    this.questionService.getAll().subscribe(data => {
      this.questions = data;
    });
    this.answerRequest = {
      body: "",
      questionId: this.questionId
    }
  }

  ngOnInit(): void {
    this.answerForm = new FormGroup({
      body: new FormControl("", Validators.required)
    });
  }

  refresh() {
    this.questionService.getAll().subscribe(data => {
      this.questions = data;
    });
  }

  saveAnswer() {
    this.answerRequest.body = this.answerForm.get("body").value;
    this.answerRequest.questionId = this.questionId;

    this.answerService.save(this.answerRequest).subscribe(() => this.refresh());
    this.answerForm.reset();
  }

}
