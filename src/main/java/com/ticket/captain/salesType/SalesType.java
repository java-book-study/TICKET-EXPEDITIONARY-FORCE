package com.ticket.captain.salesType;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "sales_type")
@Getter
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

    public SalesType() {

    }

    @Builder
    private SalesType(Long id, String name, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public void update(String name, LocalDateTime modifyDate, Long modifyId) {
        this.name = name;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }
}
