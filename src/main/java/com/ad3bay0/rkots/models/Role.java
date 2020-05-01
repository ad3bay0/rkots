package com.ad3bay0.rkots.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ad3bay0.rkots.enums.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleEnum roleName;
    
}