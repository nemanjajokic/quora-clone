package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository repository;

    /*
    public int getUserIdByUsername(String username) {
        return repository.findByUsername(username).getId();
    }
    */
}
