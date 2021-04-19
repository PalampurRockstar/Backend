package com.backend.services;

import com.backend.models.request.WeeklyBillRequest;
import com.backend.models.response.WeeklyBillResponse;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BillGenerationServiceImpl implements BillGenerationService{


    @Override
    public WeeklyBillResponse getWeeklyBill(WeeklyBillRequest weeklyBillRequest) {
        LocalDate startDate= weeklyBillRequest.getStartDate();
        LocalDate endDate= weeklyBillRequest.getEndDate();
        int perUnitRate = 2;

        Random randomUnitsEachDay = new Random();
        long noOfDaysInBetween = ChronoUnit.DAYS.between(startDate, endDate)+1;
        List<LocalDate> dateList = Stream.iterate(startDate, date -> date.plusDays(1)).limit(noOfDaysInBetween).collect(Collectors.toList());
        Map<LocalDate,BigDecimal> collectEachDayWithDateMap = new LinkedHashMap<>();

        for(int i = 0; i< dateList.size(); i++){
            int x = randomUnitsEachDay.nextInt((50));
            LocalDate eachDayDate = dateList.get(i);
            int eachDayPrice = x*perUnitRate;
            collectEachDayWithDateMap.put(eachDayDate,BigDecimal.valueOf(eachDayPrice));
        }
        final BigDecimal[] sum = {BigDecimal.ZERO};
        Map<LocalDate,BigDecimal> outputMap = new LinkedHashMap<>();

        collectEachDayWithDateMap.entrySet().forEach(each->{
            DayOfWeek day = each.getKey().getDayOfWeek();
            sum[0] = sum[0].add(each.getValue());
            if(day.toString() == "MONDAY"){
                outputMap.put(each.getKey(), sum[0]);
                sum[0] = sum[0].subtract(sum[0]);
            }
        });
        return new WeeklyBillResponse(outputMap);
    }
}
