package com.ticket.captain.festivalCategory;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "festival_category")
@Getter
@Builder
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

    @OneToOne(mappedBy = "festival")
    private Festival festival;

    public FestivalCategory() {

    }

    public FestivalCategory(Long id, String categoryName, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId, Festival festival) {
        this.id = id;
        this.categoryName = categoryName;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
        this.festival = festival;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("categoryName", categoryName)
                .append("createDate", createDate)
                .append("createId", createId)
                .append("modifyDate", modifyDate)
                .append("modifyId", modifyId)
                .append("festival", festival)
                .toString();
    }
}
