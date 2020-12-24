package io.neca.quoraclone.controller;

import io.neca.quoraclone.dto.AnswerRequest;
import io.neca.quoraclone.dto.AnswerResponse;
import io.neca.quoraclone.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAnswer(@RequestBody AnswerRequest request) {
        service.save(request);
    }

    @GetMapping("/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnswerResponse> getAll(@PathVariable int questionId) {
        return service.getAllForQuestion(questionId);
    }

}
