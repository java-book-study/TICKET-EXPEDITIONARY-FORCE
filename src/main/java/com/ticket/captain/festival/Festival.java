package com.ticket.captain.festival;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival {

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

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "create_id")
    private Long createId;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "modify_id")
    private Long modifyId;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"festival"})
    @OrderBy("id desc")
    private final Set<FestivalDetail> festival_details = new HashSet<>();

    private String festivalCategory;

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

    public void update(String title, String content, String thumbnail, LocalDateTime salesStartDate, LocalDateTime salesEndDate, LocalDateTime modifyDate, Long modifyId) {

        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }
}
