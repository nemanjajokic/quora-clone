import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Topic } from '../topic';
import { TopicService } from '../topic.service';

@Component({
  selector: 'app-create-topic',
  templateUrl: './create-topic.component.html',
  styleUrls: ['./create-topic.component.css']
})
export class CreateTopicComponent implements OnInit {

  topicForm: FormGroup;
  topic: Topic;
  name = new FormControl("");
  description = new FormControl("");

  constructor(private service: TopicService, private router: Router, private modalService: NgbModal) {
    this.topicForm = new FormGroup({
      name: new FormControl(""),
      description: new FormControl("")
    })
    this.topic = {
      name: "",
      description: ""
    }
  }

  ngOnInit(): void {
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

  save() {
    this.topic.name = this.topicForm.get("name").value;
    this.topic.description = this.topicForm.get("description").value;

    this.service.saveTopic(this.topic).subscribe(data => {
      this.close();
      this.redirectToHome();
    });
  }

  /*
  topicForm: FormGroup;
  topic: Topic;
  name = new FormControl("");
  description = new FormControl("");

  constructor(private service: TopicService, private router: Router) {
    this.topicForm = new FormGroup({
      name: new FormControl(""),
      description: new FormControl("")
    })
    this.topic = {
      name: "",
      description: ""
    }
  }

  ngOnInit(): void {
  }

  redirectToHome() {
    this.router.navigateByUrl("");
  }

  save() {
    this.topic.name = this.topicForm.get("name").value;
    this.topic.description = this.topicForm.get("description").value;

    this.service.saveTopic(this.topic).subscribe(data => {
      this.redirectToHome();
    });
  }
*/

}
