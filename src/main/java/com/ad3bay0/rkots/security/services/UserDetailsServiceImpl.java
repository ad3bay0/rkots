package com.ad3bay0.rkots.security.services;

import com.ad3bay0.rkots.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsServiceImpl implements UserDetails{
    
    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername()


}