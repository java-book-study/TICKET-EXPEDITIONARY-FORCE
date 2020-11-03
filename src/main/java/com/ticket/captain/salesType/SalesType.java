package com.ticket.captain.salesType;

import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "sales_type")
@Getter
@Builder
public class SalesType {

    @Id
    @GeneratedValue
    @Column(name = "sales_type_id")
    private Long id;

    @Column(name = "sales_type_name")
    private String name;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "create_id")
    private Long createId;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "modify_id")
    private Long modifyId;

    @OneToOne(mappedBy = "festivalDetail")
    private FestivalDetail festivalDetail;

    public SalesType() {

    }

    public SalesType(Long id, String name, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId, FestivalDetail festivalDetail) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
        this.festivalDetail = festivalDetail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("createDate", createDate)
                .append("createId", createId)
                .append("modifyDate", modifyDate)
                .append("modifyId", modifyId)
                .append("festivalDetail", festivalDetail)
                .toString();
    }
}
