package com.ticket.captain.festivalCategory;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "festival_category")
@Getter
@Builder
@ToString
public class FestivalCategory {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "create_id")
    private Long createId;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "modify_id")
    private Long modifyId;

    public FestivalCategory() {

    }

    public FestivalCategory(Long id, String categoryName, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId) {
        this.id = id;
        this.categoryName = categoryName;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }
}
