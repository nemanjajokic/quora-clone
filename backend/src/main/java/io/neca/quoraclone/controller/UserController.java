package io.neca.quoraclone.controller;

import io.neca.quoraclone.dto.UserView;
import io.neca.quoraclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{username}")
    public UserView getUserInfo(@PathVariable String username) {
        return service.getUserInfo(username);
    }

}
