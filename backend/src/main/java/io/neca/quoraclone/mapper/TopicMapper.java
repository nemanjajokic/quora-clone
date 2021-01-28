package io.neca.quoraclone.mapper;

import io.neca.quoraclone.dao.QuestionRepository;
import io.neca.quoraclone.dto.TopicDto;
import io.neca.quoraclone.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicMapper {

    @Autowired
    QuestionRepository questionRepository;

    public Topic toEntity(TopicDto topicDto) {
        return Topic.builder()
                .id(topicDto.getId())
                .name(topicDto.getName())
                .description(topicDto.getDescription())
                .build();
    }

    public TopicDto toDto(Topic topic) {
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .questionCount(questionRepository.countByTopic(topic))
                .build();
    }

}
