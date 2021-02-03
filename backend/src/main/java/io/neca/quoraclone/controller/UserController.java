package io.neca.quoraclone.controller;

import io.neca.quoraclone.dto.UserView;
import io.neca.quoraclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserView getUserInfo(@PathVariable String username) {
        return service.getUserInfo(username);
    }

}
