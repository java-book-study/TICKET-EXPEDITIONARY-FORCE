package com.ticket.captain.Festival;

import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.festival.FestivalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class FestivalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FestivalService festivalService;

    @Autowired
    FestivalRepository festivalRepository;

    private Festival festival = new Festival( randomAlphabetic(10), randomAlphabetic(40), 10);

    @BeforeEach
    void beforeEach () {
        festivalService.generate(festival);
    }

    @Test
    void festivalInfo() throws Exception {
        mockMvc.perform(get("/api/manager/1/info")
                .param("festivalId", "1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void festivals () throws Exception {
        mockMvc.perform(get("/api/manager/festivals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageable", "10")
                        .with(csrf()))
                        .andExpect(status().isOk())
                        .andDo(print());
    }
}
