package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.AnswerRepository;
import io.neca.quoraclone.dao.QuestionRepository;
import io.neca.quoraclone.dao.TopicRepository;
import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.dto.QuestionRequest;
import io.neca.quoraclone.dto.QuestionResponse;
import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.mapper.AnswerMapper;
import io.neca.quoraclone.mapper.QuestionMapper;
import io.neca.quoraclone.model.Question;
import io.neca.quoraclone.model.Topic;
import io.neca.quoraclone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private QuestionMapper mapper;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerMapper answerMapper;

    public void save(QuestionRequest request) {
        Topic topic = topicRepository.findByName(request.getTopicName());
        User user = authService.getCurrentUser();
        questionRepository.save(mapper.toEntity(request, topic, user));
    }

    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findByOrderByCreatedDesc().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public QuestionResponse getQuestion(int id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new CustomException("Question cannot be found"));

        return mapper.toDto(question);
    }

    public List<QuestionResponse> getAllQuestionsForTopic(int id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new CustomException("Topic cannot be found"));
        return questionRepository.findByTopic(topic).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
