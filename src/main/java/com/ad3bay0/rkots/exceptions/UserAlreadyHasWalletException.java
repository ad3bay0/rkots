package com.ad3bay0.rkots.exceptions;

import com.ad3bay0.rkots.models.User;

/**
 * UserAlreadyHasWalletException
 */
public class UserAlreadyHasWalletException extends Exception {

    public UserAlreadyHasWalletException(User user) {
        super("User "+user.getUsername()+" already owns a wallet : "+user.getWallet().getId());
    }
}