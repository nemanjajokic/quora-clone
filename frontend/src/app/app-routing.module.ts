import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignInComponent } from './auth/sign-in/sign-in.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { UserProfileComponent } from './profile/user-profile/user-profile.component';
import { CreateQuestionComponent } from './question/create-question/create-question.component';
import { QuestionListComponent } from './question/question-list/question-list.component';
import { TopicListComponent } from './topic/topic-list/topic-list.component';

const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "sign-in", component: SignInComponent },
  { path: "topic-list", component: TopicListComponent },
  { path: "create-question", component: CreateQuestionComponent },
  { path: "question-list", component: QuestionListComponent },
  { path: "user-profile", component: UserProfileComponent },
  { path: "header", component: HeaderComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
