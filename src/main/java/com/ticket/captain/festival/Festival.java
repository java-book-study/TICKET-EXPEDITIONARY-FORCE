package com.ticket.captain.festival;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festivalCategory.FestivalCategory;
import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Builder
@Entity
@Getter
@ToString
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private FestivalCategory festivalCategory;

    public Festival() {

    }

    public Festival(String title, String content, LocalDateTime salesStartDate, LocalDateTime salesEndDate,LocalDateTime modifyDate, Long modifyId) {
        this(null, title, null, content, salesStartDate, salesEndDate, null, null, modifyDate, modifyId, null);
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

    public Festival(Long id, String title, String thumbnail, String content, LocalDateTime salesStartDate, LocalDateTime salesEndDate, Long createId, LocalDateTime createDate, LocalDateTime modifyDate, Long modifyId, FestivalCategory festivalCategory) {
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
        this.festivalCategory = festivalCategory;
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

}
