package com.ticket.captain.salesType;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalesType is a Querydsl query type for SalesType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSalesType extends EntityPathBase<SalesType> {

    private static final long serialVersionUID = -516837729L;

    public static final QSalesType salesType = new QSalesType("salesType");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> createId = createNumber("createId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyId = createNumber("modifyId", Long.class);

    public final StringPath name = createString("name");

    public QSalesType(String variable) {
        super(SalesType.class, forVariable(variable));
    }

    public QSalesType(Path<? extends SalesType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalesType(PathMetadata metadata) {
        super(SalesType.class, metadata);
    }

}

