import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
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
  createTopicName = new FormControl("");
  createTopicDescription = new FormControl("");

  constructor(private topicService: TopicService, private questionService: QuestionService, 
    private router: Router, private service: TopicService, private modalService: NgbModal) {
    this.questionRequest = {
      name: "",
      description: "",
      topicName: ""
    }
    this.topicForm = new FormGroup({
      createTopicName: new FormControl(""),
      createTopicDescription: new FormControl("")
    })
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
      name: new FormControl(""),
      description: new FormControl(""),
      topicName: new FormControl("")
    });
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

  /*
  questionForm: FormGroup;
  topics: Array<Topic> = [];
  questionRequest: QuestionRequest;

  constructor(private topicService: TopicService, private questionService: QuestionService, private router: Router) {
    this.questionRequest = {
      name: "",
      description: "",
      topicName: ""
    }
  }

  ngOnInit(): void {
    this.topicService.getAllTopics().subscribe((data) => {
      this.topics = data;
    });
    this.questionForm = new FormGroup({
      name: new FormControl(""),
      description: new FormControl(""),
      topicName: new FormControl("")
    });
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
  */

}
