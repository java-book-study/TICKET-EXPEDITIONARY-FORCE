package com.ticket.captain.festival;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ticket.captain.common.BaseEntity;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.review.Review;
import com.ticket.captain.scrap.Scrap;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "festival_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "content")
    private String content;

    @Column(name = "sales_start_date")
    private LocalDateTime salesStartDate;

    @Column(name = "sales_end_date")
    private LocalDateTime salesEndDate;

    @JsonIgnore
    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<FestivalDetail> festival_details = new ArrayList<>();

    @OneToMany(mappedBy = "festival", fetch = FetchType.LAZY)
    private final List<Review> reviews = new ArrayList<>();

    @Column(name = "category_id")
    private String festivalCategory;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Scrap> scraps;

    public static Festival createFestival(String title, String thumbnail, String content, LocalDateTime salesStartDate, LocalDateTime salesEndDate, String festivalCategory) {
        return Festival.builder()
                .title(title)
                .thumbnail(thumbnail)
                .content(content)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .festivalCategory(festivalCategory)
                .build();
    }

    @Builder
    private Festival(String title, String thumbnail, String content, LocalDateTime salesStartDate, LocalDateTime salesEndDate, String festivalCategory) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.festivalCategory = festivalCategory;
    }

    public void update(String title, String content, String thumbnail,
                       LocalDateTime salesStartDate, LocalDateTime salesEndDate, String festivalCategory) {

        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.festivalCategory = festivalCategory;
    }
}
