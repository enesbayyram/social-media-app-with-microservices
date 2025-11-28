package com.enesbayram.sm_core.base.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private String id;

    @Column(name = "create_time" , nullable = false)
    private LocalDateTime createTime;

    @Column(name = "create_user" , nullable = false)
    private String createUser;

    @Column(name = "update_time" , nullable = true)
    private LocalDateTime updateTime;

    @Column(name = "update_user" , nullable = true)
    private String updateUser;
}
