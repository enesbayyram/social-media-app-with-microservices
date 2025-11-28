package com.enesbayram.sm_core.base.model;

import com.enesbayram.sm_core.base.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_def")
@Getter
@Setter
public class UserDef extends BaseEntity implements UserDetails {

    @Column(name = "username" , nullable = false , unique = true)
    private String username;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;

    @Lob
    @Column(name = "profile_photo" , nullable = false)
    private byte[] profilePhoto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
