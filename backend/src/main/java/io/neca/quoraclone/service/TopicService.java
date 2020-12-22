package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.TopicRepository;
import io.neca.quoraclone.dto.TopicDto;
import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.mapper.TopicMapper;
import io.neca.quoraclone.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional()
public class TopicService {

    @Autowired
    private TopicRepository repository;
    @Autowired
    private TopicMapper mapper;

    public void save(TopicDto topicDto) {
        Topic topic = mapper.toEntity(topicDto);
        repository.save(topic);
    }

    public List<TopicDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public TopicDto getTopic(int id) {
        Topic topic = repository.findById(id).orElseThrow(() -> new CustomException("Topic not found"));

        return mapper.toDto(topic);
    }

}
