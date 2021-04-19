package com.backend.services;

import com.backend.models.ElectricityReading;
import com.backend.models.PricePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PricePlanServiceTest {

    private final static String SMART_METER_ID = "smart-meter-0";
    private final static String PRICE_PLAN_ID = "price-plan-0";
    private final static String PRICE_PLAN_SP0 = "Dark energy";
    private final static String PRICE_PLAN_SP1 = "Go Green";
    private final static String PRICE_PLAN_SP2 = "Power for Everyone";
    private final static String PRICE_PLAN_ID_KEY = "pricePlanId";
    private final static String PRICE_PLAN_COMPARISONS_KEY = "pricePlanComparisons";

    //    @InjectMocks
    PricePlanService pricePlanService;

    @Mock
    MeterReadingService meterReadingService;

    List<ElectricityReading> electricityReading = new ArrayList<>();
    Map<String, String> pricePlanOfMeterId = new HashMap<>();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        List<PricePlan> pricePlansList = new ArrayList();
        pricePlansList.add(new PricePlan(PRICE_PLAN_SP0, "price-plan-0", BigDecimal.TEN));
        pricePlansList.add(new PricePlan(PRICE_PLAN_SP1, "price-plan-1", BigDecimal.valueOf(2)));
        pricePlansList.add(new PricePlan(PRICE_PLAN_SP2, "price-plan-2", BigDecimal.ONE));

        pricePlanOfMeterId.put(SMART_METER_ID, PRICE_PLAN_ID);

        electricityReading.add(new ElectricityReading(Instant.now().minusSeconds(5600), BigDecimal.valueOf(78.0)));
        electricityReading.add(new ElectricityReading(Instant.now(), BigDecimal.valueOf(56.0)));
        electricityReading.add(new ElectricityReading(Instant.now().minusSeconds(3400), BigDecimal.valueOf(29.0)));

        pricePlanService = new PricePlanService(pricePlansList, meterReadingService, pricePlanOfMeterId);

    }

    @Test
    void comparisonsOfPricePlansWithInputedMeterPricePlanTest() {
        when(meterReadingService.getMeterReadings(SMART_METER_ID)).thenReturn(Optional.of(electricityReading));
        ResponseEntity actual = pricePlanService.comparisonsOfPricePlansWithInputedMeterPricePlan(SMART_METER_ID);
        String pricePlanIdOfMeter = pricePlanOfMeterId.get(SMART_METER_ID);

        Map<String, BigDecimal> consumptionOfPlan = new HashMap<>();
        consumptionOfPlan.put("price-plan-0", BigDecimal.valueOf(349.0));
        consumptionOfPlan.put("price-plan-1", BigDecimal.valueOf(69.8));
        consumptionOfPlan.put("price-plan-2", BigDecimal.valueOf(34.9));

        Map<String, Object> expected = new HashMap<>();
        expected.put(PRICE_PLAN_ID_KEY, pricePlanIdOfMeter);
        expected.put(PRICE_PLAN_COMPARISONS_KEY, consumptionOfPlan);
        assertNotNull(actual);
        assertEquals(expected, actual.getBody());
    }

    @Test
    void positiveGetConsumptionOfElectricityReadingsforEachPricePlan() {
        when(meterReadingService.getMeterReadings(SMART_METER_ID)).thenReturn(Optional.ofNullable(electricityReading));
        Optional<Map<String,BigDecimal>> actualOutput = pricePlanService.getConsumptionOfElectricityReadingsforEachPricePlan(SMART_METER_ID);
        Map<String,BigDecimal> expectedOutput = new HashMap<>();
        expectedOutput.put("price-plan-0",BigDecimal.valueOf(349.0));
        expectedOutput.put("price-plan-1",BigDecimal.valueOf(69.8));
        expectedOutput.put("price-plan-2",BigDecimal.valueOf(34.9));
        assertNotNull(actualOutput);
        assertEquals(Optional.of(expectedOutput),actualOutput);
    }
    @Test
    void negativeGetConsumptionOfElectricityReadingsforEachPricePlan(){
        when(meterReadingService.getMeterReadings(SMART_METER_ID)).thenReturn(Optional.empty());
        Optional<Map<String,BigDecimal>> actualOutput = pricePlanService.getConsumptionOfElectricityReadingsforEachPricePlan(SMART_METER_ID);
        assertEquals(Optional.empty(),actualOutput);
    }

    @Test
    void positiveRecommendCheapestPlanTest() {
        Integer limit = 2;
        when(meterReadingService.getMeterReadings(SMART_METER_ID)).thenReturn(Optional.ofNullable(electricityReading));
        ResponseEntity actualResponse = pricePlanService.recommendCheapestPlan(SMART_METER_ID,limit);
        List<Map.Entry<String, BigDecimal>> expectedResponse = new ArrayList<Map.Entry<String, BigDecimal>>();
        expectedResponse.add(new AbstractMap.SimpleEntry<String, BigDecimal>("price-plan-2", BigDecimal.valueOf(34.9)));
        expectedResponse.add(new AbstractMap.SimpleEntry<String, BigDecimal>("price-plan-1", BigDecimal.valueOf(69.8)));
        assertNotNull(actualResponse.getBody());
        assertEquals(expectedResponse,actualResponse.getBody());

    }

    @Test
    void negativeRecommendCheapestPlanTest(){
        when(meterReadingService.getMeterReadings(SMART_METER_ID)).thenReturn(Optional.empty());
        electricityReading = new ArrayList<>();
        ResponseEntity actual = pricePlanService.recommendCheapestPlan(SMART_METER_ID,null);
        assertEquals(HttpStatus.NOT_FOUND,actual.getStatusCode());
    }
}