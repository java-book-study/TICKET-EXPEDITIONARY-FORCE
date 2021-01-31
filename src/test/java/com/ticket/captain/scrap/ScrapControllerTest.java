package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.account.WithAccount;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.security.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Slf4j
public class ScrapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Jwt jwt;

    private String jwtToken;

    private Long festivalId;

    private final static String testEmail = "testEmail";

    @BeforeAll
    public void before() {
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("Rock Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        FestivalDto festivalDto = festivalService.add(createDto);
        festivalId = festivalDto.getId();

    }

    @WithAccount(value = testEmail)
    @Test
    public void createScrapTest() throws Exception {

        //given
        mockMvc.perform(post("/api/scrap/{festivalId}", festivalId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("create-scrap",
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("festivalId").description("스크랩 할 축제 Id")
                        ),
                        responseFields(
                                fieldWithPath("scrapId").type(JsonFieldType.NUMBER).description("부여된 스크랩 id값"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("회원 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )
                ));
    }

    @WithAccount(value = testEmail)
    @Test
    public void deleteScrapTest() throws Exception {

        Account account = accountRepository.findByEmail(testEmail);
        Long scrapId = scrapService.createScrap(account, festivalId);

        mockMvc.perform(delete("/api/scrap/{scrapId}", scrapId))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
