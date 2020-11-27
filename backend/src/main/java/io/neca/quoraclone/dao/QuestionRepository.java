package io.neca.quoraclone.dao;

import io.neca.quoraclone.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
