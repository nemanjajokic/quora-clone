import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignInComponent } from './auth/sign-in/sign-in.component';
import { HomeComponent } from './home/home.component';
import { CreateQuestionComponent } from './question/create-question/create-question.component';
import { CreateTopicComponent } from './topic/create-topic/create-topic.component';
import { TopicListComponent } from './topic/topic-list/topic-list.component';

const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "sign-in", component: SignInComponent },
  { path: "topic-list", component: TopicListComponent },
  { path: "create-topic", component: CreateTopicComponent },
  { path: "create-question", component: CreateQuestionComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
