package com.backend.services;

import com.backend.generator.ElectricityReadingGenerator;
import com.backend.models.ElectricityReading;
import com.backend.models.MeterReadings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MeterReadingServiceTest.class)
class MeterReadingServiceTest {

    @InjectMocks
    MeterReadingService service;

    @BeforeEach
    void setup() {
        Map<String, List<ElectricityReading>> map = new HashMap<>();
        List<ElectricityReading> readingList = new ArrayList<>();
        map.put("smart-meter-0", readingList);
        service = new MeterReadingService(map);
    }

    @Test
    void WhenReadingsEmptyStoreMeterReadingTest() {
        List<ElectricityReading> readingList = new ArrayList<>();
        MeterReadings expected = new MeterReadings("smart-meter-0", readingList);
        ResponseEntity actualOutput = service.storeMeterReading(expected);
        assertEquals(actualOutput.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void positiveResultStoreMeterReadingTest() {
        ElectricityReadingGenerator readingGenerator = new ElectricityReadingGenerator();
        List<ElectricityReading> readingList = readingGenerator.generate(5);
        MeterReadings expectedOutput = new MeterReadings("smart-meter-0", readingList);
        ResponseEntity actualOutput = service.storeMeterReading(expectedOutput);
        assertEquals(actualOutput.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void smartMeterIdNullCaseStoreReadingTest() {
        List<ElectricityReading> readingList = new ArrayList<>();
        MeterReadings expected = new MeterReadings(null, readingList);
        ResponseEntity actualOutput = service.storeMeterReading(expected);
        assertEquals(actualOutput.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}