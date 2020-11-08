package com.ticket.captain.festival;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureRestDocs
public class FestivalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FestivalService festivalService;

    @Autowired
    FestivalRepository festivalRepository;

    public static Long FESTIVAL_TEST_ID = 1L;

    public static final String API_MANAGER_URL = "/api/manager/";

    @BeforeAll
    void beforeAll () {
        Festival festival = new Festival( randomAlphabetic(10), randomAlphabetic(10), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), FESTIVAL_TEST_ID);
        Festival generate = festivalService.generate(festival);
        System.out.println(generate.getContent());
        festivalRepository.save(festival);
    }

    @Test
    @Order(1)
    void festivalInfo() throws Exception {
        mockMvc.perform(get(API_MANAGER_URL + "1/info")
                .param("festivalId", "1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void festivals () throws Exception {
        mockMvc.perform(get(API_MANAGER_URL +"festivals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "5")
                        .with(csrf()))
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @Test
    @Order(3)
    void deleteFestival () throws Exception {
        mockMvc.perform(delete(API_MANAGER_URL + "1/del")
                .param("festivalId", "1L")
                .with(csrf()))
                .andExpect(status().is(204))
                .andDo(print());

    }
}
