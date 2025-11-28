package com.enesbayram.sm_core.base.model;

import com.enesbayram.sm_core.base.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {

    @Column(name = "picture")
    @Lob
    private  byte[] picture;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private UserDef userDef;
}
