package com.ad3bay0.rkots.util;

import java.util.Comparator;

import com.ad3bay0.rkots.models.Transaction;

public class TransactionSortingComparator implements Comparator<Transaction>{
    

    public int compare(Transaction t1, Transaction t2) {
        return t2.getCreatedAt().compareTo(t1.getCreatedAt());
    }
}