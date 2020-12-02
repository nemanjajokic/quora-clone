package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.TopicRepository;
import io.neca.quoraclone.dto.TopicDto;
import io.neca.quoraclone.mapper.TopicMapper;
import io.neca.quoraclone.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    TopicRepository repository;
    @Autowired
    TopicMapper mapper;

    public void save(TopicDto topicDto) {
        Topic topic = mapper.toEntity(topicDto);
        repository.save(topic);
    }

    public List<TopicDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
