package com.ad3bay0.rkots.exceptions;

import java.util.UUID;

public class WalletIdDoesNotExistException extends  Exception {
    public WalletIdDoesNotExistException(UUID id) {
        super("Wallet with id : "+id+" does not exist");
    }
}