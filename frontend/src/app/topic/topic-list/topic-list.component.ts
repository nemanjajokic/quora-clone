import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Topic } from '../topic';
import { TopicService } from '../topic.service';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {

  topics: Array<Topic> = [];

  constructor(private service: TopicService, private router: Router) {
    this.service.getAllTopics().subscribe(data => {
      this.topics = data;
    })
  }

  ngOnInit(): void {
  }

  showTopic(id: number) {
    this.router.navigate(["topic-show", id])
  }

}
