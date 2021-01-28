package com.ticket.captain.order;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
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
import com.ticket.captain.order.dto.OrderCreateDto;
import com.ticket.captain.order.dto.OrderDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static capital.scalable.restdocs.AutoDocumentation.*;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureRestDocs
@Slf4j
public class OrderControllerTest {

    public static final String ACCOUNT_EMAIL = "9m1i9n1@gmail.com";

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected FestivalService festivalService;
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected FestivalDetailService festivalDetailService;
    @Autowired
    private WebApplicationContext context;

    protected RestDocumentationResultHandler commonDocumentation(Snippet... snippets) {
        return document("{class-name}/{method-name}", commonResponsePreprocessor(), snippets);
    }

    protected OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(replaceBinaryContent(), limitJsonArrayLength(objectMapper),
                prettyPrint());
    }

    @Before
    @WithMockUser(value = ACCOUNT_EMAIL, roles = "MANAGER")
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(),
                        preprocessResponse(
                                replaceBinaryContent(),
                                limitJsonArrayLength(objectMapper),
                                prettyPrint())))
                .apply(documentationConfiguration(this.restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(),
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse(),
                                AutoDocumentation.requestFields(),
                                AutoDocumentation.responseFields(),
                                AutoDocumentation.pathParameters(),
                                AutoDocumentation.requestParameters(),
                                AutoDocumentation.description(),
                                AutoDocumentation.methodAndPath(),
                                AutoDocumentation.section(),
                                embedded(),
                                links()))
                .build();

        FestivalCreateDto createFestivalDto = FestivalCreateDto.builder()
                .title("TEST FESTIVAL")
                .content("TEST")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        FestivalDto festivalDto = festivalService.add(createFestivalDto);

        FestivalDetailCreateDto createFestivalDetailDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(30000L)
                .price(BigDecimal.valueOf(30000))
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .build();

        FestivalDetailDto festivalDetailDto = festivalDetailService.add(festivalDto.getId(), createFestivalDetailDto);

        OrderCreateDto orderCreateDto = orderService.createOrder(ACCOUNT_EMAIL, festivalDetailDto.getId());
    }

    @After
    @WithMockUser(value = ACCOUNT_EMAIL, roles = "MANAGER")
    public void tearDown() {
        orderRepository.deleteOrderByAccount_Email(ACCOUNT_EMAIL);
    }

    @Test
    @DisplayName("주문내역-사용자내역 조회 테스트(기간 없을경우)")
    @WithMockUser(value = ACCOUNT_EMAIL, roles = "MANAGER")
    public void find_by_accountId() throws Exception {

        //given
//        Request request = new Request();

        //then
        ResultActions result = this.mockMvc.perform(
                get("/api/order/buyer")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON));
//
        ResultActions resultActions = result.andExpect(status().isOk())
                .andDo(commonDocumentation().document(
                        embedded().documentationType(OrderDto.class),
                        responseFields().responseBodyAsType(OrderDto.class)
                ));
//                .andDo(document("{method-name}",
//                        links(
//                                linkWithRel("self").description("The <<orders, Order resource>>"),
//                                linkWithRel("next").description("The <<orders, Order resource>>").optional(),
//                                linkWithRel("last").description("The <<orders, Order resource>>").optional(),
//                                linkWithRel("first").description("The <<orders, Order resource>>").optional(),
//                                linkWithRel("document").description("해당 Api Document")
//                        ),
////                        requestFields(),
//                        responseFields(
//                                fieldWithPath("_embedded.orderDtoList[].orderNo").type(JsonFieldType.STRING).description("주문번호"),
//                                fieldWithPath("_embedded.orderDtoList[].festival").type(JsonFieldType.STRING).description("축제 정보"),
//                                fieldWithPath("_embedded.orderDtoList[].festivalDetail").type(JsonFieldType.STRING).description("축제 상세 정보"),
//                                fieldWithPath("_embedded.orderDtoList[].account").type(JsonFieldType.STRING).description("사용자 정보"),
//                                fieldWithPath("_embedded.orderDtoList[].statusCode").type(JsonFieldType.STRING).description("주문상태"),
//                                fieldWithPath("page.size").type(JsonFieldType.STRING).description("페이징 사이즈"),
//                                fieldWithPath("page.totalElements").type(JsonFieldType.STRING).description("전체 오더 개수"),
//                                fieldWithPath("page.totalPages").type(JsonFieldType.STRING).description("전체 페이지"),
//                                fieldWithPath("page.number").type(JsonFieldType.STRING).description("현재 페이지")
//                        )
//                ));
//                .andDo(document("{method-name}",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        requestFields(
//                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("조회기간(from)"),
//                                fieldWithPath("endDate").type(JsonFieldType.STRING).description("조회기간(to)")
//                        ),
//                        responseFields(
//
//                        )
//                ));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Request {
        Pageable pageable;
        LocalDate startDate;
        LocalDate endDate;
    }
}
