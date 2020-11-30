package com.ticket.captain.festival;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ticket.captain.festivalCategory.FestivalCategory;
import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


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
    private String thumbnail;

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
    private final Set<FestivalDetail> festival_details = new HashSet<>();



    public Festival() {

    }

    @Builder
    private Festival(Long id, String title, String thumbnail, String content, LocalDateTime salesStartDate, LocalDateTime salesEndDate, Long createId, LocalDateTime createDate, LocalDateTime modifyDate, Long modifyId, FestivalCategory festivalCategory) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.content = content;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
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

    public void addCategory(FestivalCategory category) {
        this.festivalCategory = category;
    }
}
