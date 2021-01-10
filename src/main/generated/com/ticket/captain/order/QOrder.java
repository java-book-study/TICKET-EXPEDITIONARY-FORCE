package com.ticket.captain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 564920175L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.ticket.captain.common.QBaseEntity _super = new com.ticket.captain.common.QBaseEntity(this);

    public final com.ticket.captain.account.QAccount account;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createId = _super.createId;

    public final com.ticket.captain.festivalDetail.QFestivalDetail festivalDetail;

    public final NumberPath<Long> festivalId = createNumber("festivalId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    //inherited
    public final StringPath modifyId = _super.modifyId;

    public final StringPath orderNo = createString("orderNo");

    public final DateTimePath<java.time.LocalDateTime> purchaseDate = createDateTime("purchaseDate", java.time.LocalDateTime.class);

    public final StringPath statusCode = createString("statusCode");

    public final ListPath<com.ticket.captain.ticket.Ticket, com.ticket.captain.ticket.QTicket> tickets = this.<com.ticket.captain.ticket.Ticket, com.ticket.captain.ticket.QTicket>createList("tickets", com.ticket.captain.ticket.Ticket.class, com.ticket.captain.ticket.QTicket.class, PathInits.DIRECT2);

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.ticket.captain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.festivalDetail = inits.isInitialized("festivalDetail") ? new com.ticket.captain.festivalDetail.QFestivalDetail(forProperty("festivalDetail"), inits.get("festivalDetail")) : null;
    }

}

