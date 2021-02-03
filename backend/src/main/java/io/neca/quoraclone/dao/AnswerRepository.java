package io.neca.quoraclone.dao;

import io.neca.quoraclone.model.Answer;
import io.neca.quoraclone.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByQuestionOrderByCreatedDesc(Question question);

    int countByQuestion(Question question);
}
