package com.ticket.captain.festival;

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
    void setUp() { }

    @Test
    @Order(1)
    void createFestival() {

        String title = randomAlphabetic(10);
        String content = randomAlphabetic(40);
        LocalDateTime salesStartDate = LocalDateTime.now();
        LocalDateTime salesEndDate = LocalDateTime.now();
        LocalDateTime modifyDate = LocalDateTime.now();
        Festival festival = new Festival(title, content, salesStartDate, salesEndDate, modifyDate, null);
        festivalService.generate(festival);
        assertThat(festival, is(notNullValue()));
        assertThat(festival.getTitle(), is(notNullValue()));
        assertThat(festival.getContent(), is(notNullValue()));
        assertThat(festival.getCreateDate(), is(notNullValue()));
        assertThat(festival.getSalesStartDate(), is(notNullValue()));
        assertThat(festival.getSalesEndDate(), is(notNullValue()));
        logger.info("Festival create {}", festival);
    }

    @Test
    @Order(2)
    void festivalInfo() {

        String title = randomAlphabetic(10);
        String content = randomAlphabetic(40);
        LocalDateTime salesStartDate = LocalDateTime.now();
        LocalDateTime salesEndDate = LocalDateTime.now();
        LocalDateTime modifyDate = LocalDateTime.now();
        Festival festival = new Festival(title, content, salesStartDate, salesEndDate, modifyDate, null);

        festivalService.generate(festival);

        Optional<Festival> firstFestival = festivalService.findById(festival.getId());
        assertThat(firstFestival, is(notNullValue()));
        assertThat(firstFestival.isEmpty(), is(false));
        logger.info("Festival Info {}", firstFestival);
    }

    @Test
    @Order(3)
    void festivals () {
        PageRequest pageable = PageRequest.of(0, 1, Sort.by("createDate").descending());
        List<Festival> festivalList = festivalService.getFestivals(pageable);
        assertThat(festivalList, is(notNullValue()));
        logger.info("Festivals {}", festivalList);
    }

    @Test
    @Order(4)
    void delFestival () {

        String title = randomAlphabetic(10);
        String content = randomAlphabetic(40);
        LocalDateTime salesStartDate = LocalDateTime.now();
        LocalDateTime salesEndDate = LocalDateTime.now();
        LocalDateTime modifyDate = LocalDateTime.now();
        Festival festival = new Festival(title, content, salesStartDate, salesEndDate, modifyDate, null);
        festivalService.generate(festival);
        festivalService.deleteFestival(festival.getId());
        assertThat(festivalService.findById(festival.getId()), is(Optional.empty()));
    }
}
