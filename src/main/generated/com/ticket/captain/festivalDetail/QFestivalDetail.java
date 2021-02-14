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

    public final com.ticket.captain.common.QBaseEntity _super = new com.ticket.captain.common.QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createId = _super.createId;

    public final DateTimePath<java.time.LocalDateTime> drawDate = createDateTime("drawDate", java.time.LocalDateTime.class);

    public final com.ticket.captain.festival.QFestival festival;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    //inherited
    public final StringPath modifyId = _super.modifyId;

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> processDate = createDateTime("processDate", java.time.LocalDateTime.class);

    public final StringPath salesType = createString("salesType");

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
        this.festival = inits.isInitialized("festival") ? new com.ticket.captain.festival.QFestival(forProperty("festival")) : null;
    }

}

