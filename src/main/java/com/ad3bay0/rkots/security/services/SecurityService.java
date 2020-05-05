package com.ad3bay0.rkots.security.services;

public interface SecurityService {

        String findLoggedInUsername();
    
        void login(String username, String password);
    
    
}