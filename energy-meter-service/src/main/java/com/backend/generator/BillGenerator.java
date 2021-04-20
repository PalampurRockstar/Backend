package com.backend.generator;


import com.backend.models.request.WeeklyBillRequest;
import com.backend.models.response.WeeklyBillResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BillGenerator {
    public WeeklyBillResponse generateBill(WeeklyBillRequest weeklyBillRequest){
        LocalDate startDate= weeklyBillRequest.getStartDate();
        LocalDate endDate= weeklyBillRequest.getEndDate();
        int perUnitRate = 2;

        Random randomUnitsEachDay = new Random();
        long noOfDaysInBetween = ChronoUnit.DAYS.between(startDate, endDate)+1;
        List<LocalDate> dateList = Stream.iterate(startDate, date -> date.plusDays(1)).limit(noOfDaysInBetween).collect(Collectors.toList());
        Map<LocalDate, BigDecimal> collectEachDayWithDateMap = new LinkedHashMap<>();

        for(int i = 0; i< dateList.size(); i++){
            int x = randomUnitsEachDay.nextInt((50));
            LocalDate eachDayDate = dateList.get(i);
            int eachDayPrice = x*perUnitRate;
            collectEachDayWithDateMap.put(eachDayDate,BigDecimal.valueOf(eachDayPrice));
        }
        return new WeeklyBillResponse(collectEachDayWithDateMap);
    }
}
