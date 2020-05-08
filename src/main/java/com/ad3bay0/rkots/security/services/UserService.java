package com.ad3bay0.rkots.security.services;
import java.util.Optional;

import com.ad3bay0.rkots.exceptions.InsufficientBalanceInWalletException;
import com.ad3bay0.rkots.exceptions.UserAlreadyHasWalletException;
import com.ad3bay0.rkots.models.User;
import com.ad3bay0.rkots.exceptions.WalletIdDoesNotExistException;

public interface UserService {

    User save(User user);

    void saveAndCreateWallet(User user) throws InsufficientBalanceInWalletException, UserAlreadyHasWalletException;

    Optional<User> findByUsername(String username);

    boolean existsByUserNameOrEmail(User user);


    String userWalletBalance(User user) throws WalletIdDoesNotExistException ;

    User getCurrentlyLoggedInUser();



}