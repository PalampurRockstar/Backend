package com.backend.config;

import com.backend.generator.ElectricityReadingGenerator;
import com.backend.models.ElectricityReading;
import com.backend.models.PricePlan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Configuration
public class ApplicationDataConfiguration {

    private static final String MOST_EVIL_PRICE_PLAN_ID = "price-plan-0";
    private static final String RENEWABLES_PRICE_PLAN_ID = "price-plan-1";
    private static final String STANDARD_PRICE_PLAN_ID = "price-plan-2";

    @Bean
    public List<PricePlan> pricePlans() {
        ArrayList<PricePlan> pricePlansList = new ArrayList();
        pricePlansList.add(new PricePlan("Evil dark energy", "price-plan-0", BigDecimal.TEN));
        pricePlansList.add(new PricePlan("Go green energy", "price-plan-1", BigDecimal.valueOf(2)));
        pricePlansList.add(new PricePlan("Power for everyone", "price-plan-2", BigDecimal.ONE));
        return pricePlansList;
    }

    @Bean
    public Map<String, List<ElectricityReading>> perMeterElectricityReadings() {
        final Map<String, List<ElectricityReading>> readings = new HashMap<>();
        final ElectricityReadingGenerator electricityReadingsGenerator = new ElectricityReadingGenerator();
        smartMeterToPricePlanAccounts()
                .keySet()
                .forEach(smartMeterId -> readings.put(smartMeterId, electricityReadingsGenerator.generate(20)));
        return readings;

    }

    @Bean
    public Map<String,String> smartMeterToPricePlanAccounts(){
        final Map<String, String> smartMeterToPricePlanAccounts = new HashMap<>();
        smartMeterToPricePlanAccounts.put("smart-meter-0", MOST_EVIL_PRICE_PLAN_ID);
        smartMeterToPricePlanAccounts.put("smart-meter-1", RENEWABLES_PRICE_PLAN_ID);
        smartMeterToPricePlanAccounts.put("smart-meter-2", MOST_EVIL_PRICE_PLAN_ID);
        smartMeterToPricePlanAccounts.put("smart-meter-3", STANDARD_PRICE_PLAN_ID);
        smartMeterToPricePlanAccounts.put("smart-meter-4", RENEWABLES_PRICE_PLAN_ID);
        return smartMeterToPricePlanAccounts;
    }
}
