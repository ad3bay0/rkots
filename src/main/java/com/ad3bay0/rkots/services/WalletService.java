package com.ad3bay0.rkots.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.ad3bay0.rkots.exceptions.InsufficientBalanceInWalletException;
import com.ad3bay0.rkots.exceptions.UserAlreadyHasWalletException;
import com.ad3bay0.rkots.exceptions.WalletIdDoesNotExistException;
import com.ad3bay0.rkots.models.Quote;
import com.ad3bay0.rkots.models.Transaction;
import com.ad3bay0.rkots.models.User;
import com.ad3bay0.rkots.models.Wallet;

public interface WalletService {

    public Wallet createWallet(UUID userId) throws InsufficientBalanceInWalletException, UserAlreadyHasWalletException;

    public BigDecimal getBalanceForWallet(UUID walletId) throws WalletIdDoesNotExistException;

    public Wallet buyFromWallet(UUID walletId, BigDecimal amount, String type)
            throws WalletIdDoesNotExistException, InsufficientBalanceInWalletException;

    public Wallet sellToWallet(UUID walletId, BigDecimal amount, String type)
            throws WalletIdDoesNotExistException;

    public List<Transaction> getTransactions(UUID walletId, Integer numberOfRecords) throws WalletIdDoesNotExistException;

    public Wallet buyStockFromWallet(User user, Quote quote)
            throws WalletIdDoesNotExistException, InsufficientBalanceInWalletException;

	public Wallet buyStockFromWallet(User user, String quoteId) throws Exception;

}