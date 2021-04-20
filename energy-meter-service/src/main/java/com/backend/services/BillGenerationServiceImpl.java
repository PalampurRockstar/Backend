package com.backend.services;

import com.backend.generator.BillGenerator;
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
        BillGenerator billGenerator = new BillGenerator();
        WeeklyBillResponse collectEachDayWithDateMap = billGenerator.generateBill(weeklyBillRequest);
        final BigDecimal[] sum = {BigDecimal.ZERO};
        Map<LocalDate,BigDecimal> outputMap = new LinkedHashMap<>();

        collectEachDayWithDateMap.getWeeklyBill().entrySet().forEach(each->{
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
