package com.enesbayram.sm_core.base.model;

import com.enesbayram.sm_core.base.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity {

    @ManyToOne
    private UserDef userDef;

    @Column(name = "refresh_token" , nullable = false)
    private String refreshToken;

    @Column(name = "expire_in" , nullable = false)
    private LocalDateTime expireIn;

}
