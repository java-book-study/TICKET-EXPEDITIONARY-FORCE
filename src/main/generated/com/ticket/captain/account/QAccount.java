package com.ticket.captain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 62590381L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccount account = new QAccount("account");

    public final com.ticket.captain.common.QBaseEntity _super = new com.ticket.captain.common.QBaseEntity(this);

    public final com.ticket.captain.common.QAddress address;

    public final ListPath<com.ticket.captain.review.Comment, com.ticket.captain.review.QComment> comments = this.<com.ticket.captain.review.Comment, com.ticket.captain.review.QComment>createList("comments", com.ticket.captain.review.Comment.class, com.ticket.captain.review.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath createId = _super.createId;

    public final StringPath email = createString("email");

    public final StringPath emailCheckToken = createString("emailCheckToken");

    public final DateTimePath<java.time.LocalDateTime> emailCheckTokenGenDate = createDateTime("emailCheckTokenGenDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    //inherited
    public final StringPath modifyId = _super.modifyId;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final ListPath<com.ticket.captain.order.Order, com.ticket.captain.order.QOrder> orders = this.<com.ticket.captain.order.Order, com.ticket.captain.order.QOrder>createList("orders", com.ticket.captain.order.Order.class, com.ticket.captain.order.QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final StringPath profileImage = createString("profileImage");

    public final ListPath<com.ticket.captain.review.Review, com.ticket.captain.review.QReview> reviews = this.<com.ticket.captain.review.Review, com.ticket.captain.review.QReview>createList("reviews", com.ticket.captain.review.Review.class, com.ticket.captain.review.QReview.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public QAccount(String variable) {
        this(Account.class, forVariable(variable), INITS);
    }

    public QAccount(Path<? extends Account> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccount(PathMetadata metadata, PathInits inits) {
        this(Account.class, metadata, inits);
    }

    public QAccount(Class<? extends Account> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.ticket.captain.common.QAddress(forProperty("address")) : null;
    }

}

