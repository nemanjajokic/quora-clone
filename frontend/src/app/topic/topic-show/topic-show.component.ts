import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AnswerRequest } from 'src/app/answer/answer-request';
import { AnswerService } from 'src/app/answer/answer.service';
import { Question } from 'src/app/question/question';
import { QuestionService } from 'src/app/question/question.service';

@Component({
    selector: 'app-topic-show',
    templateUrl: './topic-show.component.html',
    styleUrls: ['./topic-show.component.css']
})
export class TopicShowComponent implements OnInit {

    questions: Array<Question> = [];
    isCollapsed: boolean[] = [];
    topicId: number = 0;
    questionId: number = 0;

    answerForm: FormGroup;
    answerRequest: AnswerRequest;

    constructor(private questionService: QuestionService, private answerService: AnswerService, private route: ActivatedRoute) {
        this.answerRequest = {
            body: "",
            questionId: this.questionId
        }
    }

    ngOnInit(): void {
        this.topicId = this.route.snapshot.params["id"];
        this.questionService.getAllByTopic(this.topicId).subscribe(data => {
            this.questions = data;
        });
        this.answerForm = new FormGroup({
            body: new FormControl("", Validators.required)
        });
    }

    refresh() {
        this.questionService.getAllByTopic(this.topicId).subscribe(data => {
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
