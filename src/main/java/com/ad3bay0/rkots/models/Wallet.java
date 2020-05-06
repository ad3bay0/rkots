package com.ad3bay0.rkots.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallets")
@Builder
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet extends AuditModel {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> walletTransactions;

    @OneToOne
    private User user;
}