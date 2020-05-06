package com.ad3bay0.rkots.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.ad3bay0.rkots.exceptions.InsufficientBalanceInWalletException;
import com.ad3bay0.rkots.exceptions.UserAlreadyHasWalletException;
import com.ad3bay0.rkots.exceptions.WalletIdDoesNotExistException;
import com.ad3bay0.rkots.models.Transaction;
import com.ad3bay0.rkots.models.User;
import com.ad3bay0.rkots.models.Wallet;
import com.ad3bay0.rkots.repository.TransactionRepository;
import com.ad3bay0.rkots.repository.UserRepository;
import com.ad3bay0.rkots.repository.WalletRepository;
import com.ad3bay0.rkots.util.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Wallet createWallet(UUID userId) throws InsufficientBalanceInWalletException, UserAlreadyHasWalletException {

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("" + userId);
        }
        if (user.get().getWallet() != null) {

            throw new UserAlreadyHasWalletException(user.get());
        }
        return walletRepository.save(Wallet.builder().balance(new BigDecimal("1000.00")).user(user.get()).build());
    }

    @Override
    public BigDecimal getBalanceForWallet(UUID walletID) throws WalletIdDoesNotExistException {
        Optional<Wallet> wallet = walletRepository.findById(walletID);

        if (!wallet.isPresent()) {

            throw new WalletIdDoesNotExistException(walletID);
        }
        return wallet.get().getBalance();
    }

    @Override
    @Transactional
    public Wallet buyFromWallet(UUID walletId, BigDecimal amount, String type)
            throws InsufficientBalanceInWalletException, WalletIdDoesNotExistException {
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isPresent()) {

            throw new WalletIdDoesNotExistException(walletId);
        }

        BigDecimal balance = wallet.get().getBalance();
        int value = balance.compareTo(amount);

        if (value == -1) {
            throw new InsufficientBalanceInWalletException(walletId);
        }

        Wallet processWallet = wallet.get();
        processWallet.setBalance(balance.subtract(amount));

        Wallet updatedWallet = walletRepository.save(processWallet);

        if ("BUY".equals(type)) {
            saveTransaction(AppConstants.BUY, amount, AppConstants.BUY_DESCRIPTION, updatedWallet);
        }
        return updatedWallet;
    }

    @Override
    @Transactional
    public Wallet sellToWallet(UUID walletId, BigDecimal amount, String type) throws WalletIdDoesNotExistException {

        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isPresent()) {

            throw new WalletIdDoesNotExistException(walletId);
        }

        BigDecimal balance = wallet.get().getBalance();

        Wallet processWallet = wallet.get();
        processWallet.setBalance(balance.add(amount));

        Wallet updatedWallet = walletRepository.save(processWallet);

        if ("SELL".equals(type)) {
            saveTransaction(AppConstants.SELL, amount, AppConstants.SELL, updatedWallet);
        }
        return updatedWallet;
    }

    @Override
    public List<Transaction> getTransactions(UUID walletId, Integer n) throws WalletIdDoesNotExistException {
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (!wallet.isPresent()) {

            throw new WalletIdDoesNotExistException(walletId);
        }

        List<Transaction> transactions = wallet.get().getWalletTransactions();

        n = transactions.size() >= n ? n : transactions.size();
        return transactions.subList(0, n);
    }

    private void saveTransaction(String typeOfTransaction, BigDecimal amount, String description, Wallet wallet) {

        Transaction transaction = Transaction.builder().amount(amount).type(typeOfTransaction).description(description)
                .wallet(wallet).build();

        transactionRepository.save(transaction);
    }

}