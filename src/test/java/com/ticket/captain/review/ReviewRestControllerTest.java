package com.ticket.captain.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountService;
import com.ticket.captain.account.WithAccount;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.review.dto.CommentCreateDto;
import com.ticket.captain.review.dto.ReviewCreateDto;
import com.ticket.captain.review.dto.ReviewDto;
import com.ticket.captain.review.dto.ReviewUpdateDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class ReviewRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReviewService reviewService;

    @Autowired
    CommentService commentService;

    @Autowired
    FestivalService festivalService;

    @Autowired
    AccountService accountService;

    private Festival festival;

    private Account writeAccount;

    private final String REVIEW_API_ADDRESS = "/api/review/";


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
    @DisplayName("리뷰 작성")
    @WithAccount("eunseong")
    public void reviewWrite() throws Exception {
        ReviewCreateDto createDto = reviewCreateDtoSample();

        MvcResult mvcResult = mockMvc.perform(post(REVIEW_API_ADDRESS)
                            .contentType(MediaTypes.HAL_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(createDto)))
                            .andExpect(status().isOk())
                            .andDo(document("review_write_success",
                                    getDocumentRequest(),
                                    getDocumentResponse(),
                                    requestFields(
                                            fieldWithPath("title").type(JsonFieldType.STRING).description("review title"),
                                            fieldWithPath("contents").type(JsonFieldType.STRING).description("review contents"),
                                            fieldWithPath("festivalId").type(JsonFieldType.NUMBER).description("festivalId")
                                    ),
                                    responseFields(
                                            fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 id"),
                                            fieldWithPath("title").type(JsonFieldType.STRING).description("리뷰 제목"),
                                            fieldWithPath("contents").type(JsonFieldType.STRING).description("리뷰 내용"),
                                            fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자"),
                                            fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("댓글수"),
                                            fieldWithPath("festivalId").type(JsonFieldType.NUMBER).description("페스티벌 id"),
                                            fieldWithPath("createId").type(JsonFieldType.STRING).description("작성자 id"),
                                            fieldWithPath("createDate").type(JsonFieldType.STRING).description("생성날짜"),
                                            fieldWithPath("modifyId").type(JsonFieldType.STRING).description("수정자 id"),
                                            fieldWithPath("modifyDate").type(JsonFieldType.STRING).description("수정날짜"),
                                            fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("리뷰 경로"),
                                            fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                                    )))
                            .andDo(print())
                            .andReturn();
    }

    @Test
    @DisplayName("리뷰 작성 실패")
    @WithAccount("eunseong")
    public void reviewWriteFail() throws Exception{
        ReviewCreateDto createDto = ReviewCreateDto.builder()
                .title("title")
                .contents("content")
                .festivalId(0L)
                .build();

        MvcResult mvcResult = mockMvc.perform(post(REVIEW_API_ADDRESS)
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(createDto)))
                .andExpect(status().is(404))
                .andDo(document("review_write_fail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andDo(print())
                .andReturn();

    }

    @Test
    @DisplayName("리뷰 조회 - 댓글 제외")
    @WithAccount("eunseong")
    public  void reviewDetail() throws Exception {
        ReviewDto reviewDto = reviewCreate("eunseong@naver.com");

        MvcResult mvcResult = mockMvc.perform(get(REVIEW_API_ADDRESS + reviewDto.getId())
                .contentType(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("detail-account",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("리뷰 제목"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("댓글수"),
                                fieldWithPath("festivalId").type(JsonFieldType.NUMBER).description("페스티벌 id"),
                                fieldWithPath("createId").type(JsonFieldType.STRING).description("작성자 id"),
                                fieldWithPath("createDate").type(JsonFieldType.STRING).description("생성날짜"),
                                fieldWithPath("modifyId").type(JsonFieldType.STRING).description("수정자 id"),
                                fieldWithPath("modifyDate").type(JsonFieldType.STRING).description("수정날짜"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("리뷰 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("리뷰 조회 - 댓글 포함")
    @WithAccount("eunseong")
    public void reviewAndCommentDetail() throws Exception {
        ReviewDto reviewDto = reviewCreate("eunseong@naver.com");

        CommentCreateDto createDto = createDto(reviewDto);
        commentService.create(createDto, writeAccount.getId());

        MvcResult mvcResult = mockMvc.perform(get(REVIEW_API_ADDRESS + "reviewCommentDetail/" + reviewDto.getId())
                .contentType(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("리뷰 수정")
    @WithAccount("eunseong")
    public void reviewUpdate() throws Exception {
        ReviewDto reviewDto = reviewCreate("eunseong@naver.com");

        ReviewUpdateDto updateDto = ReviewUpdateDto.builder()
                .reviewId(reviewDto.getId())
                .contents("content Update")
                .title("title Update")
                .build();

        MvcResult mvcResult = mockMvc.perform(put(REVIEW_API_ADDRESS)
                                .contentType(MediaTypes.HAL_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(updateDto)))
                                .andExpect(status().isOk())
                                .andDo(document("review_write_success",
                                        getDocumentRequest(),
                                        getDocumentResponse(),
                                        requestFields(
                                                fieldWithPath("title").type(JsonFieldType.STRING).description("review title"),
                                                fieldWithPath("contents").type(JsonFieldType.STRING).description("review contents"),
                                                fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("reviewId")
                                        ),
                                        responseFields(
                                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 id"),
                                                fieldWithPath("title").type(JsonFieldType.STRING).description("리뷰 제목"),
                                                fieldWithPath("contents").type(JsonFieldType.STRING).description("리뷰 내용"),
                                                fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자"),
                                                fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("댓글수"),
                                                fieldWithPath("festivalId").type(JsonFieldType.NUMBER).description("페스티벌 id"),
                                                fieldWithPath("createId").type(JsonFieldType.STRING).description("작성자 id"),
                                                fieldWithPath("createDate").type(JsonFieldType.STRING).description("생성날짜"),
                                                fieldWithPath("modifyId").type(JsonFieldType.STRING).description("수정자 id"),
                                                fieldWithPath("modifyDate").type(JsonFieldType.STRING).description("수정날짜"),
                                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("리뷰 경로"),
                                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                                        )))
                                .andDo(print())
                                .andReturn();

        reviewService.delete(reviewDto.getId(), writeAccount.getId());
    }

    @Test
    @DisplayName("리뷰 수정실패")
    @WithAccount("eunseong")
    public void reviewUpdateFail() throws Exception {
        ReviewDto reviewDto = reviewCreate("oceana57@naver.com");

        ReviewUpdateDto updateDto = ReviewUpdateDto.builder()
                .reviewId(reviewDto.getId())
                .contents("content Update")
                .title("title Update")
                .build();


        MvcResult mvcResult = mockMvc.perform(put(REVIEW_API_ADDRESS)
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().is(401))
                .andDo(document("review_write_fail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("review title"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("review contents"),
                                fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("reviewId")
                        ),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andDo(print())
                .andReturn();

    }

    @Test
    @DisplayName("리뷰 삭제")
    @WithAccount("eunseong")
    public void reviewDelete() throws Exception{
        ReviewDto reviewDto = reviewCreate("eunseong@naver.com");

        MvcResult mvcResult = mockMvc.perform(delete(REVIEW_API_ADDRESS + reviewDto.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }


    @Test
    @DisplayName("리뷰 삭제실패")
    @WithAccount("eunseong")
    public void reviewDeleteFail() throws Exception{
        ReviewDto reviewDto = reviewCreate("oceana57@naver.com");

        MvcResult mvcResult = mockMvc.perform(delete(REVIEW_API_ADDRESS + reviewDto.getId()))
                .andExpect(status().is(401))
                .andDo(document("review_delete_fail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andReturn();
    }

    @AfterEach
    void afterAll() {
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
