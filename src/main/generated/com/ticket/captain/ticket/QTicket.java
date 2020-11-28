package com.ticket.captain.ticket;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTicket is a Querydsl query type for Ticket
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTicket extends EntityPathBase<Ticket> {

    private static final long serialVersionUID = -900907281L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTicket ticket = new QTicket("ticket");

    public final com.ticket.captain.festivalDetail.QFestivalDetail festivalDetail;

    public final com.ticket.captain.order.QOrder order;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath statusCode = createString("statusCode");

    public final NumberPath<Long> ticketId = createNumber("ticketId", Long.class);

    public final StringPath ticketNo = createString("ticketNo");

    public QTicket(String variable) {
        this(Ticket.class, forVariable(variable), INITS);
    }

    public QTicket(Path<? extends Ticket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTicket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTicket(PathMetadata metadata, PathInits inits) {
        this(Ticket.class, metadata, inits);
    }

    public QTicket(Class<? extends Ticket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.festivalDetail = inits.isInitialized("festivalDetail") ? new com.ticket.captain.festivalDetail.QFestivalDetail(forProperty("festivalDetail"), inits.get("festivalDetail")) : null;
        this.order = inits.isInitialized("order") ? new com.ticket.captain.order.QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

