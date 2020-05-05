package com.ad3bay0.rkots.models;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String fullName;

    @NotNull
	@Size(max=20,min=2)
	@Column(unique=true)
	@NotBlank (message = "Username is mandatory")
	private String username;

    @Column(unique = true)
	@Size(max = 50)
	@Email
	private String email;

	@NotNull
	@JsonIgnore
	@Column
	@NotBlank (message = "Password is mandatory")
	private String password;

	@Transient
	private String passwordConfirm;

	@ManyToMany(mappedBy = "users")
	private Set<Role> roles;
    
}