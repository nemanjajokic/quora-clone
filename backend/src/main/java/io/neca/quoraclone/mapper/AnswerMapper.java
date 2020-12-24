package io.neca.quoraclone.mapper;

import io.neca.quoraclone.dto.AnswerRequest;
import io.neca.quoraclone.dto.AnswerResponse;
import io.neca.quoraclone.model.Answer;
import io.neca.quoraclone.model.Question;
import io.neca.quoraclone.model.User;
import io.neca.quoraclone.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class AnswerMapper {

    @Autowired
    TimeUtil timeUtil;

    // Question and user objects required for relations
    public Answer toEntity(AnswerRequest request, Question question, User user) {
        return Answer.builder()
                .id(request.getId())
                .body(request.getBody())
                .question(question)
                .user(user)
                .created(Instant.now())
                .build();
    }

    public AnswerResponse toDto(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .body(answer.getBody())
                .questionId(answer.getQuestion().getId())
                .userName(answer.getUser().getUsername())
                // duration
                .duration(timeUtil.toDuration(answer.getCreated()))
                .build();
    }

}
