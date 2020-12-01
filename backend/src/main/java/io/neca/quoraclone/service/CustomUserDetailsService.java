package io.neca.quoraclone.service;

import io.neca.quoraclone.dao.UserRepository;
import io.neca.quoraclone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null)
            throw new UsernameNotFoundException("User " + username + " not found");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isVerified(),
                true, true, true,
                Collections.singleton(new SimpleGrantedAuthority("USER")));
    }
}
