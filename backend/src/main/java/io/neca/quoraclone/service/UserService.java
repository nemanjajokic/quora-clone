package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.dto.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserView getUserInfo(String username) {
        return userRepository.findUserInfoByUsername(username);
    }

}
