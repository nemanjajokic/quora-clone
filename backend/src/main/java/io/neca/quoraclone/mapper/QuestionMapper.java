package io.neca.quoraclone.mapper;

import io.neca.quoraclone.dto.QuestionRequest;

import io.neca.quoraclone.dto.QuestionResponse;
import io.neca.quoraclone.model.Question;
import io.neca.quoraclone.model.Topic;
import io.neca.quoraclone.model.User;
import io.neca.quoraclone.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Optional;

@Configuration
public class QuestionMapper {

    @Autowired
    private TimeUtil timeUtil;

    // Topic and user objects required for relations
    public Question toEntity(QuestionRequest request, Topic topic, User user) {
        return Question.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .created(Instant.now())
                .topic(topic)
                .user(user)
                .build();
    }

    public QuestionResponse toDto(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .name(question.getName())
                .description(question.getDescription())
                .topicName(question.getTopic().getName())
                .userName(question.getUser().getUsername())
                .imageUri(Optional.ofNullable(question.getUser().getImageUri()).orElse(null))   // If exists
                // duration
                .duration(timeUtil.toDuration(question.getCreated()))
                .build();
    }

}
