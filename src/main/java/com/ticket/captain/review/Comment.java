package com.ticket.captain.review;

import com.ticket.captain.account.Account;
import com.ticket.captain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String contents;

    @Column
    private String writer;

    @Column
    private Integer level;

    private Boolean live;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "super_comment_id")
    private Comment superComment;

    @OneToMany(mappedBy = "superComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> subComment = new ArrayList<>();

    @Builder
    public Comment(String contents, String writer, Integer level, Boolean live,
                   Comment superComment, Review review, Account account) {
        this.contents = contents;
        this.writer = writer;
        this.level = level;
        this.live = live;
        this.superComment = superComment;
        this.review = review;
        this.account = account;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("contents", contents)
                .append("writer", writer)
                .append("level", level)
                .append("live", live)
                .toString();
    }

    public void apply(String contents) {
        this.contents = contents;
    }
}
