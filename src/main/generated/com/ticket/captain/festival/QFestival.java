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

    public static final QFestival festival = new QFestival("festival");

    public final com.ticket.captain.common.QBaseEntity _super = new com.ticket.captain.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createId = _super.createId;

    public final ListPath<com.ticket.captain.festivalDetail.FestivalDetail, com.ticket.captain.festivalDetail.QFestivalDetail> festival_details = this.<com.ticket.captain.festivalDetail.FestivalDetail, com.ticket.captain.festivalDetail.QFestivalDetail>createList("festival_details", com.ticket.captain.festivalDetail.FestivalDetail.class, com.ticket.captain.festivalDetail.QFestivalDetail.class, PathInits.DIRECT2);

    public final StringPath festivalCategory = createString("festivalCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    //inherited
    public final StringPath modifyId = _super.modifyId;

    public final ListPath<com.ticket.captain.review.Review, com.ticket.captain.review.QReview> reviews = this.<com.ticket.captain.review.Review, com.ticket.captain.review.QReview>createList("reviews", com.ticket.captain.review.Review.class, com.ticket.captain.review.QReview.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> salesEndDate = createDateTime("salesEndDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> salesStartDate = createDateTime("salesStartDate", java.time.LocalDateTime.class);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    public QFestival(String variable) {
        super(Festival.class, forVariable(variable));
    }

    public QFestival(Path<? extends Festival> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFestival(PathMetadata metadata) {
        super(Festival.class, metadata);
    }

}

