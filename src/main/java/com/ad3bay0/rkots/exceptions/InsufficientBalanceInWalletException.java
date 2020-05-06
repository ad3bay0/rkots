package com.ad3bay0.rkots.exceptions;

import java.util.UUID;

public class InsufficientBalanceInWalletException extends Exception{
    
    public InsufficientBalanceInWalletException(UUID id) {
        super("Wallet with id : "+id+" does not have sufficient balance");
    }
}