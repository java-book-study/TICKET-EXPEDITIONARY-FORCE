package com.ticket.captain.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.enumType.SalesType;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festivalDetail.FestivalDetailService;
import com.ticket.captain.festivalDetail.dto.FestivalDetailCreateDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailDto;
import com.ticket.captain.order.dto.OrderDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Slf4j
public class OrderControllerTest {

    public static final String ACCOUNT_EMAIL = "9m1i9n1@gmail.com";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected FestivalService festivalService;
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected FestivalDetailService festivalDetailService;
    private FestivalDto festivalDto;
    private FestivalDetailDto festivalDetailDto;
    private OrderDto orderDto;

    @BeforeAll
    @Transactional
    @WithMockUser(value = ACCOUNT_EMAIL, roles = "MANAGER")
    public void setUp() {
        FestivalCreateDto createFestivalDto = FestivalCreateDto.builder()
                .title("TEST FESTIVAL")
                .content("TEST")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        festivalDto = festivalService.add(createFestivalDto);

        FestivalDetailCreateDto createFestivalDetailDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(30000L)
                .price(BigDecimal.valueOf(30000))
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .build();

        festivalDetailDto = festivalDetailService.add(festivalDto.getId(), createFestivalDetailDto);

        orderDto = orderService.createOrder(ACCOUNT_EMAIL, festivalDetailDto.getId());
    }

    @AfterAll
    @Transactional
    @WithMockUser(value = ACCOUNT_EMAIL, roles = "MANAGER")
    public void tearDown() {
        festivalDetailService.delete(festivalDetailDto.getId());
        festivalService.delete(festivalDto.getId());
        orderService.deleteByOrderNo(orderDto.getOrderNo());
    }

    @Test
    @DisplayName("주문내역-사용자내역 조회 테스트(기간 없을경우)")
    @WithMockUser(value = ACCOUNT_EMAIL, roles = "MANAGER")
    public void find_by_accountId() throws Exception {
        //then
        ResultActions result = this.mockMvc.perform(
                get("/api/order/buyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON));

        result.andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        links(
                                linkWithRel("self").description("해당 링"),
                                linkWithRel("next").description("다음 링크").optional(),
                                linkWithRel("last").description("이전 링크").optional(),
                                linkWithRel("first").description("처음 링크").optional(),
                                linkWithRel("document").description("해당 Api Document")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.orderDtoList[].orderNo").type(JsonFieldType.STRING).description("주문번호"),
                                fieldWithPath("_embedded.orderDtoList[].festival.id").type(JsonFieldType.NUMBER).description("축제 코드"),
                                fieldWithPath("_embedded.orderDtoList[].festival.title").type(JsonFieldType.STRING).description("축제명"),
                                fieldWithPath("_embedded.orderDtoList[].festival.thumbnail").type(JsonFieldType.STRING).description("축제 썸네일").optional(),
                                fieldWithPath("_embedded.orderDtoList[].festival.content").type(JsonFieldType.STRING).description("축제 내용"),
                                fieldWithPath("_embedded.orderDtoList[].festival.salesStartDate").type(JsonFieldType.STRING).description("축제 시작일"),
                                fieldWithPath("_embedded.orderDtoList[].festival.salesEndDate").type(JsonFieldType.STRING).description("축제 종료일"),
                                fieldWithPath("_embedded.orderDtoList[].festival.createId").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("_embedded.orderDtoList[].festival.createDate").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("_embedded.orderDtoList[].festival.modifyDate").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("_embedded.orderDtoList[].festival.modifyId").type(JsonFieldType.STRING).description("수정"),
                                fieldWithPath("_embedded.orderDtoList[].festival.festivalCategory").type(JsonFieldType.STRING).description("축제 종류"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.id").type(JsonFieldType.NUMBER).description("축제 상세 코드"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.salesType").type(JsonFieldType.STRING).description("축제 판매 종류"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.amount").type(JsonFieldType.NUMBER).description("축제 티켓량"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.price").type(JsonFieldType.NUMBER).description("축제 티켓가격"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.processDate").type(JsonFieldType.STRING).description("축제일"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.drawDate").type(JsonFieldType.STRING).description("추첨일"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.createDate").type(JsonFieldType.STRING).description("생성"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.createId").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.modifyDate").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.modifyId").type(JsonFieldType.STRING).description("수정자"),
                                fieldWithPath("_embedded.orderDtoList[].account.id").type(JsonFieldType.NUMBER).description("사용자 코드"),
                                fieldWithPath("_embedded.orderDtoList[].account.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("_embedded.orderDtoList[].account.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("_embedded.orderDtoList[].account.nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("_embedded.orderDtoList[].account.point").type(JsonFieldType.NUMBER).description("포인트"),
                                fieldWithPath("_embedded.orderDtoList[].account.address.city").type(JsonFieldType.STRING).description("도시"),
                                fieldWithPath("_embedded.orderDtoList[].account.address.street").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("_embedded.orderDtoList[].account.address.zipcode").type(JsonFieldType.STRING).description("우편번호"),
                                fieldWithPath("_embedded.orderDtoList[].account.role").type(JsonFieldType.STRING).description("권한"),
                                fieldWithPath("_embedded.orderDtoList[].account.emailCheckToken").type(JsonFieldType.STRING).description("이메일체크토큰"),
                                fieldWithPath("_embedded.orderDtoList[].account.emailCheckTokenGenDate").type(JsonFieldType.STRING).description("이메일체크토큰 생성일"),
                                fieldWithPath("_embedded.orderDtoList[].statusCode").type(JsonFieldType.STRING).description("주문상태"),
                                fieldWithPath("_embedded.orderDtoList[]._links.self.href").type(JsonFieldType.STRING).description("링크"),
                                fieldWithPath("_links.first.href").type(JsonFieldType.STRING).description("첫번째 링크").optional(),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("링크"),
                                fieldWithPath("_links.next.href").type(JsonFieldType.STRING).description("다음 링크").optional(),
                                fieldWithPath("_links.last.href").type(JsonFieldType.STRING).description("이전 링크").optional(),
                                fieldWithPath("_links.document.href").type(JsonFieldType.STRING).description("도큐먼트 링크"),
                                fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("페이징 사이즈"),
                                fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("전체 개수"),
                                fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지"),
                                fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("현재 페이지")
                        )
                ));
    }

    @Test
    @DisplayName("주문내역-사용자내역 조회 테스트(기간 있을경우)")
    @WithMockUser(value = ACCOUNT_EMAIL, roles = "MANAGER")
    public void find_by_Date() throws Exception {

        //given
        Request request = new Request();
        request.startDate = "20210128";
        request.endDate = "20210128";

        //then
        ResultActions result = this.mockMvc.perform(
                get("/api/order/buyer")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON));

        result.andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        links(
                                linkWithRel("self").description("해당 링"),
                                linkWithRel("next").description("다음 링크").optional(),
                                linkWithRel("last").description("이전 링크").optional(),
                                linkWithRel("first").description("처음 링크").optional(),
                                linkWithRel("document").description("해당 Api Document")
                        ),
                        requestFields(
                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("일자(from)"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).description("일자(to)")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.orderDtoList[].orderNo").type(JsonFieldType.STRING).description("주문번호"),
                                fieldWithPath("_embedded.orderDtoList[].festival.id").type(JsonFieldType.NUMBER).description("축제 코드"),
                                fieldWithPath("_embedded.orderDtoList[].festival.title").type(JsonFieldType.STRING).description("축제명"),
                                fieldWithPath("_embedded.orderDtoList[].festival.thumbnail").type(JsonFieldType.STRING).description("축제 썸네일").optional(),
                                fieldWithPath("_embedded.orderDtoList[].festival.content").type(JsonFieldType.STRING).description("축제 내용"),
                                fieldWithPath("_embedded.orderDtoList[].festival.salesStartDate").type(JsonFieldType.STRING).description("축제 시작일"),
                                fieldWithPath("_embedded.orderDtoList[].festival.salesEndDate").type(JsonFieldType.STRING).description("축제 종료일"),
                                fieldWithPath("_embedded.orderDtoList[].festival.createId").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("_embedded.orderDtoList[].festival.createDate").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("_embedded.orderDtoList[].festival.modifyDate").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("_embedded.orderDtoList[].festival.modifyId").type(JsonFieldType.STRING).description("수정"),
                                fieldWithPath("_embedded.orderDtoList[].festival.festivalCategory").type(JsonFieldType.STRING).description("축제 종류"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.id").type(JsonFieldType.NUMBER).description("축제 상세 코드"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.salesType").type(JsonFieldType.STRING).description("축제 판매 종류"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.amount").type(JsonFieldType.NUMBER).description("축제 티켓량"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.price").type(JsonFieldType.NUMBER).description("축제 티켓가격"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.processDate").type(JsonFieldType.STRING).description("축제일"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.drawDate").type(JsonFieldType.STRING).description("추첨일"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.createDate").type(JsonFieldType.STRING).description("생성"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.createId").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.modifyDate").type(JsonFieldType.STRING).description("수정일"),
                                fieldWithPath("_embedded.orderDtoList[].festivalDetail.modifyId").type(JsonFieldType.STRING).description("수정자"),
                                fieldWithPath("_embedded.orderDtoList[].account.id").type(JsonFieldType.NUMBER).description("사용자 코드"),
                                fieldWithPath("_embedded.orderDtoList[].account.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("_embedded.orderDtoList[].account.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("_embedded.orderDtoList[].account.nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("_embedded.orderDtoList[].account.point").type(JsonFieldType.NUMBER).description("포인트"),
                                fieldWithPath("_embedded.orderDtoList[].account.address.city").type(JsonFieldType.STRING).description("도시"),
                                fieldWithPath("_embedded.orderDtoList[].account.address.street").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("_embedded.orderDtoList[].account.address.zipcode").type(JsonFieldType.STRING).description("우편번호"),
                                fieldWithPath("_embedded.orderDtoList[].account.role").type(JsonFieldType.STRING).description("권한"),
                                fieldWithPath("_embedded.orderDtoList[].account.emailCheckToken").type(JsonFieldType.STRING).description("이메일체크토큰"),
                                fieldWithPath("_embedded.orderDtoList[].account.emailCheckTokenGenDate").type(JsonFieldType.STRING).description("이메일체크토큰 생성일"),
                                fieldWithPath("_embedded.orderDtoList[].statusCode").type(JsonFieldType.STRING).description("주문상태"),
                                fieldWithPath("_embedded.orderDtoList[]._links.self.href").type(JsonFieldType.STRING).description("링크"),
                                fieldWithPath("_links.first.href").type(JsonFieldType.STRING).description("첫번째 링크").optional(),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("링크"),
                                fieldWithPath("_links.next.href").type(JsonFieldType.STRING).description("다음 링크").optional(),
                                fieldWithPath("_links.last.href").type(JsonFieldType.STRING).description("이전 링크").optional(),
                                fieldWithPath("_links.document.href").type(JsonFieldType.STRING).description("도큐먼트 링크"),
                                fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("페이징 사이즈"),
                                fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("전체 개수"),
                                fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지"),
                                fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("현재 페이지")
                        )
                ));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Request {
        Pageable pageable;
        String startDate;
        String endDate;
    }
}
