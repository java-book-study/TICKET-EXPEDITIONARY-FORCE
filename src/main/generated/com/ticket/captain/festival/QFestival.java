package com.ticket.captain.festival;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFestival is a Querydsl query type for Festival
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFestival extends EntityPathBase<Festival> {

    private static final long serialVersionUID = 903952239L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFestival festival = new QFestival("festival");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> createId = createNumber("createId", Long.class);

    public final SetPath<com.ticket.captain.festivalDetail.FestivalDetail, com.ticket.captain.festivalDetail.QFestivalDetail> festival_details = this.<com.ticket.captain.festivalDetail.FestivalDetail, com.ticket.captain.festivalDetail.QFestivalDetail>createSet("festival_details", com.ticket.captain.festivalDetail.FestivalDetail.class, com.ticket.captain.festivalDetail.QFestivalDetail.class, PathInits.DIRECT2);

    public final com.ticket.captain.festivalCategory.QFestivalCategory festivalCategory;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyId = createNumber("modifyId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> salesEndDate = createDateTime("salesEndDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> salesStartDate = createDateTime("salesStartDate", java.time.LocalDateTime.class);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    public QFestival(String variable) {
        this(Festival.class, forVariable(variable), INITS);
    }

    public QFestival(Path<? extends Festival> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFestival(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFestival(PathMetadata metadata, PathInits inits) {
        this(Festival.class, metadata, inits);
    }

    public QFestival(Class<? extends Festival> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.festivalCategory = inits.isInitialized("festivalCategory") ? new com.ticket.captain.festivalCategory.QFestivalCategory(forProperty("festivalCategory")) : null;
    }

}

