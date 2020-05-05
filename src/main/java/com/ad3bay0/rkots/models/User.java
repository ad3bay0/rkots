package com.ad3bay0.rkots.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
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
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotNull
	@JsonIgnore
	@Column
	@NotBlank (message = "Password is mandatory")
	private String password;
    
}