package io.neca.quoraclone.dao;

import io.neca.quoraclone.dto.UserView;
import io.neca.quoraclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    UserView findUserInfoByUsername(String username);
}
