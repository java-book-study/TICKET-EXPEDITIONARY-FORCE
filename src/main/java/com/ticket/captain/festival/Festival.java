package com.ticket.captain.festival;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festivalCategory.FestivalCategory;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.review.Review;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Builder
@Entity
@Getter
public class Festival {

    @Id
    @GeneratedValue
    @Column(name = "festival_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "thumbnail")
    private String Thumbnail;

    @Column(name = "content")
    private String content;

    @Column(name = "sales_start_date")
    private LocalDateTime salesStartDate;

    @Column(name = "sales_end_date")
    private LocalDateTime salesEndDate;

    @Column(name = "create_id")
    private Long createId;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "modify_id")
    private Long modifyId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "festival", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"festival"})
    @OrderBy("id desc")
    private final List<FestivalDetail> festival_details = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "festival", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"festival"})
    @OrderBy("id desc")
    private final List<Review> review = new ArrayList<>();

    @OneToOne
    private FestivalCategory festivalCategory;

    public Festival() {

    }

    public Festival(String title, String content, String thumbnail, LocalDateTime salesStartDate, LocalDateTime salesEndDate, Long createId, LocalDateTime modifyDate, Long modifyId) {

        this.title = title;
        this.content = content;
        this.Thumbnail = thumbnail;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.createId = createId;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
    }

    public Festival(Long id, String title, String thumbnail, String content, LocalDateTime salesStartDate, LocalDateTime salesEndDate, Long createId, LocalDateTime createDate, LocalDateTime modifyDate, Long modifyId) {
        this.id = id;
        this.title = title;
        Thumbnail = thumbnail;
        this.content = content;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public void update(FestivalCreateDto requestDto) {
        checkArgument(isNotEmpty(title), "name must be provided.");
        checkArgument(
                title.length() >= 1 && title.length() <= 20,
                "title length must be between 1 and 10 characters."
        );
        checkArgument(isNotEmpty(content), "content must be provided.");
        checkArgument(
                content.length() >= 1 && content.length() <= 1000,
                "content length must be between 1 and 1000 characters."
        );
        this.title = requestDto.getTitle();
        this.Thumbnail = requestDto.getThumbnail();
        this.content = requestDto.getContent();
        this.salesStartDate = requestDto.getSalesStartDate();
        this.salesEndDate = requestDto.getSalesEndDate();
        this.createId = requestDto.getCreateId();
        this.modifyDate = requestDto.getModifyDate();
        this.modifyId = requestDto.getModifyId();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("Thumbnail", Thumbnail)
                .append("content", content)
                .append("salesStartDate", salesStartDate)
                .append("salesEndDate", salesEndDate)
                .append("createId", createId)
                .append("createDate", createDate)
                .append("modifyDate", modifyDate)
                .append("modifyId", modifyId)
                .append("festival_details", festival_details)
                .append("review", review)
                .append("festivalCategory", festivalCategory)
                .toString();
    }
}
