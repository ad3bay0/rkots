package com.ad3bay0.rkots.models;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quotes")
@Builder(toBuilder=true)
@AllArgsConstructor
@Getter@Setter
@NoArgsConstructor
public class Quote extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique=true)
    private String symbol;
    private String companyName;
    private String primaryExchange;
    private String calculationPrice;
    private BigDecimal latestPrice;
    private BigDecimal previousClose;
    private String latestSource;
    
}