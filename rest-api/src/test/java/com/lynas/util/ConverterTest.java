package com.lynas.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConverterTest {

    @Autowired
    Converter converter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void longToStringDate() {
        String dateString = converter.longToStringDate(1637840755629L);
        assertEquals("2021-11-25 17:45:55", dateString);
    }
}