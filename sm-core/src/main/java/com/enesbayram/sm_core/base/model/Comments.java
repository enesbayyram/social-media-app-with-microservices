package com.enesbayram.sm_core.base.model;

import com.enesbayram.sm_core.base.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comments extends BaseEntity {

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private UserDef userDef;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comments parentComment;

}
