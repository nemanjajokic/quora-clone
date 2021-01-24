import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Topic } from 'src/app/topic/topic';
import { TopicService } from 'src/app/topic/topic.service';
import { QuestionRequest } from '../question-request';
import { QuestionService } from '../question.service';

@Component({
    selector: 'app-create-question',
    templateUrl: './create-question.component.html',
    styleUrls: ['./create-question.component.css']
})
export class CreateQuestionComponent implements OnInit {

    questionForm: FormGroup;
    topics: Array<Topic> = [];
    questionRequest: QuestionRequest;

    topicForm: FormGroup;
    topic: Topic;

    constructor(private topicService: TopicService, private questionService: QuestionService,
        private router: Router, private service: TopicService, private modalService: NgbModal) {
        this.questionRequest = {
            name: "",
            description: "",
            topicName: ""
        }
        this.topic = {
            name: "",
            description: ""
        }
    }

    ngOnInit(): void {
        this.topicService.getAllTopics().subscribe((data) => {
            this.topics = data;
        });
        this.questionForm = new FormGroup({
            name: new FormControl("", Validators.required),
            description: new FormControl("", Validators.required),
            topicName: new FormControl("", Validators.required)
        });
        this.topicForm = new FormGroup({
            createTopicName: new FormControl("", Validators.required),
            createTopicDescription: new FormControl("", Validators.required)
        })
    }

    open(content) {
        this.modalService.open(content);
    }

    close() {
        this.modalService.dismissAll();
    }

    redirectToHome() {
        this.router.navigateByUrl("");
    }

    saveQuestion() {
        this.questionRequest.name = this.questionForm.get("name").value;
        this.questionRequest.description = this.questionForm.get("description").value;
        this.questionRequest.topicName = this.questionForm.get("topicName").value;

        this.questionService.save(this.questionRequest).subscribe((data) => {
            this.redirectToHome();
        })
    }

    saveTopic() {
        this.topic.name = this.topicForm.get("createTopicName").value;
        this.topic.description = this.topicForm.get("createTopicDescription").value;

        this.service.saveTopic(this.topic).subscribe((res) => {
            this.topicService.getAllTopics().subscribe((data) => {
                this.topics = data;
            });
            this.close();
        });
    }

}
