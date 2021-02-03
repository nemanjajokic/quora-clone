package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.AnswerRepository;
import io.neca.quoraclone.dao.QuestionRepository;
import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.dto.AnswerRequest;
import io.neca.quoraclone.dto.AnswerResponse;
import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.mapper.AnswerMapper;
import io.neca.quoraclone.model.Question;
import io.neca.quoraclone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private AnswerMapper mapper;

    public void save(AnswerRequest request) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new CustomException("Question cannot be found"));
        User user = authService.getCurrentUser();
        answerRepository.save(mapper.toEntity(request, question, user));
    }

    public List<AnswerResponse> getAllForQuestion(int questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException("Question cannot be found"));

        return answerRepository.findByQuestionOrderByCreatedDesc(question).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
