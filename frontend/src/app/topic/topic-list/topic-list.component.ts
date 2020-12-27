import { Component, OnInit } from '@angular/core';
import { Topic } from '../topic';
import { TopicService } from '../topic.service';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {

  topics: Array<Topic> = [];

  constructor(private service: TopicService) {
    this.service.getAllTopics().subscribe(data => {
      this.topics = data;
    })
  }

  ngOnInit(): void {
  }

}
