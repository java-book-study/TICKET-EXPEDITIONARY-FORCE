package com.ticket.captain.ticket;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer amount;

    @Builder
    private Ticket(Long id, String title, Integer amount) {
        this.id = id;
        this.title = title;
        this.amount = amount;
    }

    public void update(Integer amount) {
        this.amount = amount;
    }
}