package io.neca.quoraclone.dao;

import io.neca.quoraclone.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query(value = "select * from question where topic_id = ?1", nativeQuery = true)
    List<Question> findByTopicId(int id);
}
