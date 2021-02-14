package com.ticket.captain.scrap;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScrap is a Querydsl query type for Scrap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QScrap extends EntityPathBase<Scrap> {

    private static final long serialVersionUID = 932172341L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScrap scrap = new QScrap("scrap");

    public final com.ticket.captain.account.QAccount account;

    public final com.ticket.captain.festival.QFestival festival;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QScrap(String variable) {
        this(Scrap.class, forVariable(variable), INITS);
    }

    public QScrap(Path<? extends Scrap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScrap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScrap(PathMetadata metadata, PathInits inits) {
        this(Scrap.class, metadata, inits);
    }

    public QScrap(Class<? extends Scrap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.ticket.captain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.festival = inits.isInitialized("festival") ? new com.ticket.captain.festival.QFestival(forProperty("festival")) : null;
    }

}

