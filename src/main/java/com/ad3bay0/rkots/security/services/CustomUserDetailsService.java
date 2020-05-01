package com.ad3bay0.rkots.security.services;

import java.util.UUID;

import javax.transaction.Transactional;

import com.ad3bay0.rkots.models.User;
import com.ad3bay0.rkots.repository.UserRepository;
import com.ad3bay0.rkots.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username : "+username));

        return UserPrincipal.createUser(user);
    }

    @Transactional
    public UserDetails loadUserById(UUID id){
        User user = userRepository.findById(id).orElseThrow( ()->new UsernameNotFoundException("User not found with id: "+id));

        return UserPrincipal.createUser(user);
    }
}
 
