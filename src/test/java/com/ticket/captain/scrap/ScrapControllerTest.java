package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.account.WithAccount;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
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

    private Long festivalId;

    private final static String testEmail = "testEmail@naver.com";

    @BeforeAll
    public void before(){
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
    public void createScrapTest() throws Exception{
        //given
        mockMvc.perform(post("/scrap/{festivalId}", festivalId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithAccount(value= testEmail)
    @Test
    public void deleteScrapTest() throws Exception {

        Account account = accountRepository.findByEmail(testEmail);
        Long scrapId = scrapService.createScrap(account, festivalId);

        mockMvc.perform(delete("/scrap/{scrapId}", scrapId))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
