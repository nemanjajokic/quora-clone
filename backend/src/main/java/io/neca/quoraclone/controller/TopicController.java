package io.neca.quoraclone.controller;

import io.neca.quoraclone.dto.TopicDto;
import io.neca.quoraclone.model.Topic;
import io.neca.quoraclone.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicService service;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTopic(@RequestBody TopicDto topicDto) {
        service.save(topicDto);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<TopicDto> getAll() {
        return service.getAll();
    }

}
