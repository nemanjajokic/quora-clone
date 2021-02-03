package io.neca.quoraclone.dao;

import io.neca.quoraclone.model.Question;
import io.neca.quoraclone.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByOrderByCreatedDesc();

    List<Question> findByTopic(Topic topic);

    int countByTopic(Topic topic);
}
