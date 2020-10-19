package com.ticket.captain.Festival;

import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FestivalServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FestivalService festivalService;

    @BeforeAll
    void setUp() {

    }

    @Test
    @Order(1)
    void createFestival() {
        String name = randomAlphabetic(10);
        String Thumbnail = randomAlphabetic(10);
        String content = randomAlphabetic(40);
        int winners = 0;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        Festival festival = festivalService.generate(new Festival(name, content, winners, Thumbnail, startDate, endDate));
        assertThat(festival, is(notNullValue()));
        assertThat(festival.getName(), is(notNullValue()));
        assertThat(festival.getContent(), is(notNullValue()));
        assertThat(festival.getWinners(), is(winners));
        assertThat(festival.getThumbnail(), is(notNullValue()));
        assertThat(festival.getStartDate(), is(notNullValue()));
        assertThat(festival.getEndDate(), is(notNullValue()));
        logger.info("Festival create {}", festival);
    }

    @Test
    @Order(2)
    void festivalInfo() {
        String name = randomAlphabetic(10);
        String Thumbnail = randomAlphabetic(10);
        String content = randomAlphabetic(40);
        int winners = 0;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        Festival newFestival = festivalService.generate(new Festival(name, content, winners, Thumbnail, startDate, endDate));

        Optional<Festival> firstFestival = festivalService.findById(newFestival.getId());
        assertThat(firstFestival, is(notNullValue()));
        assertThat(firstFestival.isEmpty(), is(false));
        logger.info("Festival Info {}", firstFestival);
    }

    @Test
    @Order(3)
    void festivals () {
        PageRequest pageable = PageRequest.of(0, 12, Sort.by("createDate").descending());
        List<Festival> festivalList = festivalService.getFestivals(pageable);
        assertThat(festivalList, is(notNullValue()));
        logger.info("Festivals {}", festivalList);
    }
}
