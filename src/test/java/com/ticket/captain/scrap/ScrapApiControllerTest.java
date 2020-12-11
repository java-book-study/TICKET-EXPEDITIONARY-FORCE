package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.account.WithAccount;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.enumType.SalesType;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festivalDetail.FestivalDetailService;
import com.ticket.captain.festivalDetail.dto.FestivalDetailCreateDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrapApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private FestivalDetailService festivalDetailService;

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private AccountRepository accountRepository;

    public static FestivalDto festivalDto;

    public static FestivalDetailDto festivalDetailDto;

    private final String testEmail = "eunseong@naver.com";

    @Before
    public void setUp() {

        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("Rock Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        festivalDto = festivalService.add(createDto);

        FestivalDetailCreateDto festivalDetailCreateDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(10000L)
                .price(20000L)
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .build();

        festivalDetailDto = festivalDetailService.add(festivalDto.getId(), festivalDetailCreateDto);

    }

    @Test
    @WithAccount("eunseong@naver.com")
    public void createScrap() throws Exception {

        //when
        mockMvc.perform(put("/api/scrap/{id}", festivalDetailDto.getId())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
        //then
    }

    @Test
    @WithAccount("eunseong@naver.com")
    public void deleteScrap() throws Exception {

        //when
        mockMvc.perform(delete("/api/scrap/{id}", festivalDetailDto.getId())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        //then
    }

    @Test
    @WithAccount(testEmail)
    public void viewFestivalDetailByScrap() throws Exception {

        Account account = accountRepository.findByEmail(testEmail);

        scrapService.createScrap(account.getId(), festivalDetailDto.getId());

        mockMvc.perform(get("/api/mypage/scraps")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}