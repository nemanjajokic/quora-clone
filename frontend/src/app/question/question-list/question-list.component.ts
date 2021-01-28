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
        this.answerRequest = {
            body: "",
            questionId: this.questionId
        }
        this.questionService.getAllQuestions().subscribe(questions => {
            this.questions = questions;
        });
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

    getAnswers() {
        this.answerService.getAllAnswersForQuestion(this.questionId).subscribe(answers => {
            let index = this.questions.findIndex(q => q.id===this.questionId);
            this.questions[index].answers = answers;
        });
    }

    refreshAnswers(id: number) {
        this.answerService.getAllAnswersForQuestion(id).subscribe(answers => {
            let index = this.questions.findIndex(q => q.id===id);
            this.questions[index].answers = answers;
        });
    }

    saveAnswer(id: number) {
        this.answerRequest.body = this.answerForm.get("body").value;
        this.answerRequest.questionId = id;
        this.answerService.save(this.answerRequest).subscribe(() => this.refreshAnswers(id));
        this.answerForm.reset();
    }

}
