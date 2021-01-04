import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SignInComponent } from './auth/sign-in/sign-in.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { TokenInterceptor } from './auth/token-interceptor';
import { TopicListComponent } from './topic/topic-list/topic-list.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CreateQuestionComponent } from './question/create-question/create-question.component';
import { QuestionListComponent } from './question/question-list/question-list.component';
import { FeedComponent } from './feed/feed.component';
import { UserProfileComponent } from './profile/user-profile/user-profile.component';
import { TopicShowComponent } from './topic/topic-show/topic-show.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignInComponent,
    HomeComponent,
    TopicListComponent,
    FeedComponent,
    CreateQuestionComponent,
    QuestionListComponent,
    UserProfileComponent,
    TopicShowComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxWebstorageModule.forRoot(),
    NgbModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
