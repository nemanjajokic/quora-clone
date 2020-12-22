package io.neca.quoraclone.dao;

import io.neca.quoraclone.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Topic findByName(String name);
}
