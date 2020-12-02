package io.neca.quoraclone.mapper;

import io.neca.quoraclone.dto.TopicDto;
import io.neca.quoraclone.model.Topic;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicMapper {

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
                .build();
    }

}
