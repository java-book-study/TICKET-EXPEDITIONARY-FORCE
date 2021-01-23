package com.ticket.captain.review;

import com.ticket.captain.account.Account;
import com.ticket.captain.common.BaseEntity;
import com.ticket.captain.festival.Festival;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private String writer;

    @Column
    @Setter
    private int commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToMany(mappedBy = "review" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @Builder
    public Review(String title, String contents, String writer,
                  int commentCount, Account account, Festival festival) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.commentCount = commentCount;
        this.account = account;
        this.festival = festival;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("contents", contents)
                .append("writer", writer)
                .append("commentCount", commentCount)
                .toString();
    }

    public void apply(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void commentCountPlus() {
        this.commentCount++;
    }
}
