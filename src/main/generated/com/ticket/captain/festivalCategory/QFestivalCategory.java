package com.ticket.captain.festivalCategory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.ticket.captain.enumType.FestivalCategory;


/**
 * QFestivalCategory is a Querydsl query type for FestivalCategory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFestivalCategory extends EntityPathBase<FestivalCategory> {

    private static final long serialVersionUID = 1176780591L;

    public static final QFestivalCategory festivalCategory = new QFestivalCategory("festivalCategory");

    public final StringPath categoryName = createString("categoryName");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> createId = createNumber("createId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyId = createNumber("modifyId", Long.class);

    public QFestivalCategory(String variable) {
        super(FestivalCategory.class, forVariable(variable));
    }

    public QFestivalCategory(Path<? extends FestivalCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFestivalCategory(PathMetadata metadata) {
        super(FestivalCategory.class, metadata);
    }

}

