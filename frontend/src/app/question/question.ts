import { AnswerResponse } from "../answer/answer-response";

export class Question {
    id: number;
    name: string;
    description: string;
    topicName: string;
    userName: string;
    imageUri: string;
    duration: string;
    answers: Array<AnswerResponse>;
}