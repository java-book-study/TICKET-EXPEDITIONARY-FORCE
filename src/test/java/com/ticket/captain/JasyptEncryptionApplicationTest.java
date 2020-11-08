package com.ticket.captain;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class JasyptEncryptionApplicationTest {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;

    @Test
    public void jasypt_Bean생성확인() {
        assertThat(jasyptStringEncryptor).isNotNull();
    }

    @Test
    public void jasypt_암호화_복호화() {

        String orgText = "test";
        log.info("기본 값 :" + orgText);

        String encText = jasyptStringEncryptor.encrypt(orgText);

        log.info("암호화 값 :" + encText);
        String decText = jasyptStringEncryptor.decrypt(encText);

        log.info("복호화 값 :" + decText);
        assertThat(decText).isEqualTo(orgText);

    }
}
