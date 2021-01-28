package com.ticket.captain.review;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.exception.UnauthorizedException;
import com.ticket.captain.review.dto.CommentCreateDto;
import com.ticket.captain.review.dto.CommentDto;
import com.ticket.captain.review.dto.CommentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;
    private final ReviewRepository reviewRepository;

    public CommentDto create(CommentCreateDto createDto, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(NotFoundException::new);
        Review review = reviewRepository.findById(createDto.getReviewId())
                .orElseThrow(NotFoundException::new);

        if (createDto.getSuperCommentId() != null) {
            Comment newComment =  subCommentOf(createDto, account, review);
            accountAndReviewAddComment(newComment, account, review);
            return CommentDto.of(newComment);
        }

        Comment newComment = of(createDto, account, review);
        accountAndReviewAddComment(newComment, account, review);

        return CommentDto.of(newComment);
    }

    private Comment of(CommentCreateDto createDto, Account account, Review review) {

        return Comment.builder()
                .contents(createDto.getContents())
                .writer(account.getNickname())
                .live(true)
                .level(1)
                .review(review)
                .account(account)
                .build();
    }

    private Comment subCommentOf(CommentCreateDto createDto, Account account, Review review) {
        Comment supComment = commentRepository.findById(createDto.getSuperCommentId())
                .orElseThrow(NotFoundException::new);

        if (!supComment.getLive()) {
            throw new RuntimeException("comment is not exist");
        }

        Comment newComment = Comment.builder()
                    .contents(createDto.getContents())
                    .writer(account.getNickname())
                    .live(true)
                    .level(supComment.getLevel() + 1)
                    .review(review)
                    .superComment(supComment)
                    .account(account)
                    .build();

        supComment.getSubComment().add(newComment);
        commentRepository.save(supComment);

        return commentRepository.save(newComment);
    }

    private void accountAndReviewAddComment(Comment newComment, Account account, Review review) {
        account.getComments().add(newComment);
        accountRepository.save(account);

        review.getComments().add(newComment);
        review.commentCountPlus();
        reviewRepository.save(review);
    }

    public CommentDto update(CommentUpdateDto updateDto, Long accountId) {
        Comment comment = commentRepository.findById(updateDto.getCommentId())
                .orElseThrow(NotFoundException::new);

        if (comment.getAccount().getId() != accountId) {
            throw new UnauthorizedException();
        }

        comment.apply(updateDto.getContents());
        return CommentDto.of(comment);
    }

    public void delete(Long commentId, Long accountId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundException::new);

        if (comment.getAccount().getId() != accountId) {
            throw new UnauthorizedException();
        }

        commentRepository.delete(comment);
    }
}
