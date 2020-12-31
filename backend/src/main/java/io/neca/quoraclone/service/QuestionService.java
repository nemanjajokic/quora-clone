package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.AnswerRepository;
import io.neca.quoraclone.dao.QuestionRepository;
import io.neca.quoraclone.dao.TopicRepository;
import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.dto.AnswerResponse;
import io.neca.quoraclone.dto.QuestionAnswerResponse;
import io.neca.quoraclone.dto.QuestionRequest;
import io.neca.quoraclone.dto.QuestionResponse;
import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.mapper.AnswerMapper;
import io.neca.quoraclone.mapper.QuestionMapper;
import io.neca.quoraclone.model.Answer;
import io.neca.quoraclone.model.Question;
import io.neca.quoraclone.model.Topic;
import io.neca.quoraclone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
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
    private AnswerMapper aMapper;

    public void save(QuestionRequest request) {
        Topic topic = topicRepository.findByName(request.getTopicName());
        User user = authService.getCurrentUser();
        questionRepository.save(mapper.toEntity(request, topic, user));
    }

    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public QuestionResponse getQuestion(int id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new CustomException("Question cannot be found"));

        return mapper.toDto(question);
    }

    public List<QuestionAnswerResponse> getAll() {
//        List<QuestionResponse> questions = getAllQuestions();
//        List<AnswerResponse> answers = questions.stream().map(q -> answerRepository.findAllByQuestion(q)).collect(Collectors.toList());
        List<Question> questions = questionRepository.findAll();
        List<QuestionAnswerResponse> responses = new LinkedList<>();
        for(Question q : questions) {
           QuestionAnswerResponse response = new QuestionAnswerResponse();
           List<AnswerResponse> answerResponses = answerRepository.findAllByQuestion(q).stream().map(aMapper::toDto).collect(Collectors.toList());
           response.setId(q.getId());
           response.setName(q.getName());
           response.setDescription(q.getDescription());
           response.setTopicName(q.getTopic().getName());
           response.setUserName(q.getUser().getUsername());
           response.setDuration(q.getCreated().toString());
           response.setAnswers(answerResponses);

           responses.add(response);
        }

        return responses;
    }

}
