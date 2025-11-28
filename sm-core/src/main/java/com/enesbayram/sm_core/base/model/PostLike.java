package com.enesbayram.sm_core.base.model;

import com.enesbayram.sm_core.base.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_like")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLike extends BaseEntity {

    @ManyToOne(optional = false)
    private Post post;

    @ManyToOne(optional = false)
    private UserDef userDef;
}
