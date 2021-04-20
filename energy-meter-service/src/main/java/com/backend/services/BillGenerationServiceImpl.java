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
        WeeklyBillResponse collectEachDayWithDateResponse = billGenerator.generateBill(weeklyBillRequest);
        List<BigDecimal>valueCollector = new ArrayList();
        Map<LocalDate,BigDecimal> outputMap = new LinkedHashMap<>();

        collectEachDayWithDateResponse.getWeeklyBill().entrySet().forEach(each->{
            DayOfWeek day = each.getKey().getDayOfWeek();
            valueCollector.add(each.getValue());
            BigDecimal resultSum = valueCollector.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if(day.toString().equals("MONDAY")){
                outputMap.put(each.getKey(), resultSum);
                valueCollector.clear();
            }
        });
        return new WeeklyBillResponse(outputMap);
    }
}
