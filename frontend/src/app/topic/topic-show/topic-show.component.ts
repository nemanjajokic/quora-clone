import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AnswerRequest } from 'src/app/answer/answer-request';
import { AnswerService } from 'src/app/answer/answer.service';
import { AuthService } from 'src/app/auth/auth.service';
import { ProfileService } from 'src/app/profile/profile.service';
import { UserView } from 'src/app/profile/user-view';
import { Question } from 'src/app/question/question';
import { QuestionService } from 'src/app/question/question.service';
import { Topic } from '../topic';
import { TopicService } from '../topic.service';

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
    topic: Topic;
    
    get topicName() { return (this.topic && this.topic.name) ? this.topic.name : null }
    get topicDescription() { return (this.topic && this.topic.description) ? this.topic.description : null }

    username: string;
    isLoggedIn: boolean;
    userView: any;

    answerForm: FormGroup;
    answerRequest: AnswerRequest;

    constructor(private questionService: QuestionService, private topicService: TopicService, private answerService: AnswerService, 
        private authService: AuthService, private profileService: ProfileService, private route: ActivatedRoute) {

        this.answerRequest = {
            body: "",
            questionId: this.questionId
        }
        this.topicId = this.route.snapshot.params["id"];

        this.topicService.getTopic(this.topicId).subscribe(data => {
            this.topic = data;
        });
        this.questionService.getAllByTopic(this.topicId).subscribe(results => {
            for(let q of results) {
                this.answerService.getAllAnswersForQuestion(q.id).subscribe(answers => {
                    q.answers = answers;
                });
                this.questions.push(q);
            }
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

    refresh() {
        this.questionService.getAllByTopic(this.topicId).subscribe(results => {
            this.questions = [];
            for(let q of results) {
                this.answerService.getAllAnswersForQuestion(q.id).subscribe(answers => {
                    q.answers = answers;
                });
                this.questions.push(q);
            }
        });
    }

    saveAnswer() {
        this.answerRequest.body = this.answerForm.get("body").value;
        this.answerRequest.questionId = this.questionId;

        this.answerService.save(this.answerRequest).subscribe(() => this.refresh());
        this.answerForm.reset();
    }

}
