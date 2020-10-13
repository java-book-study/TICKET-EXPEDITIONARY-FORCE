package com.ticket.captain.festival;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Builder
@Entity
@Table(name = "Festival")
@EqualsAndHashCode(of = "id")
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "festival_id")
    private Long id;

    @Column(name = "festival_name")
    private String name;

    private String Thumbnail;

    @Column(name = "content")
    private String content;

    @Column(name = "winners")
    private int winners;

    @Column(name = "festival_start_date")
    private LocalDateTime startDate;

    @Column(name = "festival_end_date")
    private LocalDateTime endDate;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    public Festival() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public String getContent() {
        return content;
    }

    public int getWinners() {
        return winners;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }


    public Festival(String name, String content, int winners) {
        this(null, name, null, content, winners, null, null, null);
    }

    public Festival(String name, String content, int winners, String thumbnail) {
        this(null, name, thumbnail, content, winners, null, null, null);
    }

    public Festival(String name, String content, int winners, String thumbnail, LocalDateTime startDate, LocalDateTime endDate) {
        this(null, name, thumbnail, content, winners, startDate, endDate, null);
    }

    public Festival(Long id, String name, String thumbnail, String content, int winners, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createDate) {
        checkArgument(isNotEmpty(name), "name must be provided.");
        checkArgument(
                name.length() >= 1 && name.length() <= 20,
                "name length must be between 1 and 10 characters."
        );
        checkArgument(isNotEmpty(content), "content must be provided.");
        checkArgument(
                content.length() >= 1 && content.length() <= 1000,
                "content length must be between 1 and 1000 characters."
        );

        this.id = id;
        this.name = name;
        this.Thumbnail = thumbnail;
        this.content = content;
        this.winners = winners;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = defaultIfNull(createDate, now());
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("Thumbnail", Thumbnail)
                .append("content", content)
                .append("winners", winners)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .append("createDate", createDate)
                .toString();
    }
}
