package com.enesbayram.sm_api_gateway.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_def")
@Getter
@Setter
public class UserDef extends BaseEntity{

    @Column(name = "username" , nullable = false , unique = true)
    private String username;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;

}
