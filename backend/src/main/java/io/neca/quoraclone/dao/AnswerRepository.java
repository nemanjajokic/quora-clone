package io.neca.quoraclone.dao;

import io.neca.quoraclone.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
