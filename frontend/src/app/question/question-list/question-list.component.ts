import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AnswerRequest } from 'src/app/answer/answer-request';
import { AnswerService } from 'src/app/answer/answer.service';
import { AuthService } from 'src/app/auth/auth.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { UserView } from 'src/app/profile/user-view';
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

    username: string;
    isLoggedIn: boolean;
    userView: any;

    answerForm: FormGroup;
    answerRequest: AnswerRequest;

    constructor(private questionService: QuestionService, private answerService: AnswerService, 
        private authService: AuthService, private profileService: ProfileService) {

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
        this.authService.loggedIn.subscribe((data: boolean) => this.isLoggedIn = data);
        this.authService.userName.subscribe((data: string) => this.username = data);
        this.isLoggedIn = this.authService.isLoggedIn();
        this.username = this.authService.getUserName();

        this.profileService.userView.subscribe((data: UserView) => this.userView = data);
        this.profileService.getUserInfo(this.username).subscribe(data => {
            this.userView = data;
            console.log(data);
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
