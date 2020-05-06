package com.ad3bay0.rkots.security.services;

import java.util.HashSet;
import java.util.Optional;

import com.ad3bay0.rkots.exceptions.InsufficientBalanceInWalletException;
import com.ad3bay0.rkots.exceptions.UserAlreadyHasWalletException;
import com.ad3bay0.rkots.exceptions.WalletIdDoesNotExistException;
import com.ad3bay0.rkots.models.User;
import com.ad3bay0.rkots.repository.RoleRepository;
import com.ad3bay0.rkots.repository.UserRepository;
import com.ad3bay0.rkots.services.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private WalletService walletService;

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUserNameOrEmail(User user) {

        return userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent();
    }

    @Override
    public String userWalletBalance(User user) throws WalletIdDoesNotExistException{
        
        return walletService.getBalanceForWallet(user.getWallet().getId()).toString();
    }

    @Override
    public void saveAndCreateWallet(User user)
            throws InsufficientBalanceInWalletException, UserAlreadyHasWalletException {

        User userSaved = save(user);
        
        walletService.createWallet(userSaved.getId());

    }

    
    
}