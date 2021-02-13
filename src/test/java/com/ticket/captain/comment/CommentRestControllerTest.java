package com.ticket.captain.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountService;
import com.ticket.captain.account.WithAccount;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.review.Comment;
import com.ticket.captain.review.CommentService;
import com.ticket.captain.review.Review;
import com.ticket.captain.review.ReviewService;
import com.ticket.captain.review.dto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class CommentRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CommentService commentService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    FestivalService festivalService;

    @Autowired
    AccountService accountService;

    private Festival festival;

    private Account writeAccount;

    private ReviewDto review;

    private final String COMMENT_API_ADDRESS = "/api/comment/";

    @BeforeEach
    void setUp() {
        FestivalCreateDto festivalCreateDto = FestivalCreateDto.builder()
                .title("festival")
                .content("festivals")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory("ROCK")
                .build();

        FestivalDto festivalDto = festivalService.add(festivalCreateDto);
        festival = festivalService.findByTitle(festivalDto.getTitle());

    }

    @Test
    @DisplayName("댓글 생성")
    @WithAccount("eunseong")
    public void commentCreate() throws Exception {
        review = reviewCreate("eunseong@naver.com");
        CommentCreateDto createDto = createDto(review);

        MvcResult mvcResult = mockMvc.perform(post(COMMENT_API_ADDRESS)
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andDo(document("comment_write_success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("review title"),
                                fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("review contents"),
                                fieldWithPath("superCommentId").type(JsonFieldType.NUMBER).description("festivalId")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("댓글 id"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("댓글내용"),
                                fieldWithPath("writer").type(JsonFieldType.STRING).description("댓글 작성자"),
                                fieldWithPath("level").type(JsonFieldType.NUMBER).description("depth"),
                                fieldWithPath("live").type(JsonFieldType.BOOLEAN).description("삭제여부"),
                                fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("리뷰 id"),
                                fieldWithPath("superCommentId").type(JsonFieldType.NUMBER).description("상위 댓글 id"),
                                fieldWithPath("createId").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("createDate").type(JsonFieldType.STRING).description("생성날짜"),
                                fieldWithPath("modifyId").type(JsonFieldType.STRING).description("수정자 id"),
                                fieldWithPath("modifyDate").type(JsonFieldType.STRING).description("수정날짜"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("댓글 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("댓글 수정")
    @WithAccount("eunseong")
    public void commentUpdate() throws Exception{
        review = reviewCreate("eunseong@naver.com");
        CommentCreateDto createDto = createDto(review);
        CommentDto comment = commentService.create(createDto, writeAccount.getId());

        CommentUpdateDto updateDto = new CommentUpdateDto("updateComment", review.getId(), comment.getId());

        MvcResult mvcResult = mockMvc.perform(put(COMMENT_API_ADDRESS)
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andDo(document("comment_update_success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("review title"),
                                fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("review contents"),
                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("festivalId")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("댓글 id"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("댓글내용"),
                                fieldWithPath("writer").type(JsonFieldType.STRING).description("댓글 작성자"),
                                fieldWithPath("level").type(JsonFieldType.NUMBER).description("depth"),
                                fieldWithPath("live").type(JsonFieldType.BOOLEAN).description("삭제여부"),
                                fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("리뷰 id"),
                                fieldWithPath("superCommentId").type(JsonFieldType.NUMBER).description("상위 댓글 id"),
                                fieldWithPath("createId").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("createDate").type(JsonFieldType.STRING).description("생성날짜"),
                                fieldWithPath("modifyId").type(JsonFieldType.STRING).description("수정자 id"),
                                fieldWithPath("modifyDate").type(JsonFieldType.STRING).description("수정날짜"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("댓글 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("댓글 삭제")
    @WithAccount("eunseong")
    public void commentDelete() throws Exception {
        review = reviewCreate("eunseong@naver.com");
        CommentCreateDto createDto = createDto(review);
        CommentDto comment = commentService.create(createDto, writeAccount.getId());

        MvcResult mvcResult = mockMvc.perform(delete(COMMENT_API_ADDRESS + comment.getId())
                .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("댓글 조회 - 리뷰 제외 댓글만")
    @WithAccount("eunseong")
    public void commentDetail() throws Exception {
        review = reviewCreate("eunseong@naver.com");
        CommentCreateDto createDto = createDto(review);
        CommentDto comment = commentService.create(createDto, writeAccount.getId());

        MvcResult mvcResult = mockMvc.perform(get(COMMENT_API_ADDRESS + "review/" + review.getId())
                .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @AfterEach
    void afterAll() {
        reviewService.delete(review.getId(), writeAccount.getId());
        festivalService.delete(festival.getId());
    }

    private ReviewCreateDto reviewCreateDtoSample() {
        String title = "review";
        String content = "contents";
        Long festivalId = festival.getId();

        return ReviewCreateDto.builder()
                .title(title)
                .contents(content)
                .festivalId(festivalId)
                .build();
    }

    private ReviewDto reviewCreate(String email) {
        Account account = accountService.findByEmail(email);
        writeAccount = account;
        return reviewService.add(reviewCreateDtoSample(), account.getId(), festival.getId());
    }

    private CommentCreateDto createDto(ReviewDto review) {
        return new CommentCreateDto("comment", review.getId(), 2024L);
    }
}
