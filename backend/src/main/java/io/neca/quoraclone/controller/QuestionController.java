package io.neca.quoraclone.controller;

import io.neca.quoraclone.dto.QuestionRequest;
import io.neca.quoraclone.dto.QuestionResponse;
import io.neca.quoraclone.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestion(@RequestBody QuestionRequest request) {
        service.save(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponse> getAll() {
        return service.getAllQuestions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponse getQuestion(@PathVariable int id) {
        return service.getQuestion(id);
    }

    @GetMapping("/topic/{id}")
    public List<QuestionResponse> getAllForTopic(@PathVariable int id) {
        return service.getAllQuestionsForTopic(id);
    }

}
