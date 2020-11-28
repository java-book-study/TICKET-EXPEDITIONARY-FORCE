package com.ticket.captain.festivalDetail;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFestivalDetail is a Querydsl query type for FestivalDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFestivalDetail extends EntityPathBase<FestivalDetail> {

    private static final long serialVersionUID = -2027748017L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFestivalDetail festivalDetail = new QFestivalDetail("festivalDetail");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> createId = createNumber("createId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> drawDate = createDateTime("drawDate", java.time.LocalDateTime.class);

    public final com.ticket.captain.festival.QFestival festival;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyId = createNumber("modifyId", Long.class);

    public final ListPath<com.ticket.captain.order.Order, com.ticket.captain.order.QOrder> orders = this.<com.ticket.captain.order.Order, com.ticket.captain.order.QOrder>createList("orders", com.ticket.captain.order.Order.class, com.ticket.captain.order.QOrder.class, PathInits.DIRECT2);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final DateTimePath<java.time.LocalDateTime> processDate = createDateTime("processDate", java.time.LocalDateTime.class);

    public final com.ticket.captain.salesType.QSalesType salesType;

    public QFestivalDetail(String variable) {
        this(FestivalDetail.class, forVariable(variable), INITS);
    }

    public QFestivalDetail(Path<? extends FestivalDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFestivalDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFestivalDetail(PathMetadata metadata, PathInits inits) {
        this(FestivalDetail.class, metadata, inits);
    }

    public QFestivalDetail(Class<? extends FestivalDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.festival = inits.isInitialized("festival") ? new com.ticket.captain.festival.QFestival(forProperty("festival"), inits.get("festival")) : null;
        this.salesType = inits.isInitialized("salesType") ? new com.ticket.captain.salesType.QSalesType(forProperty("salesType")) : null;
    }

}

