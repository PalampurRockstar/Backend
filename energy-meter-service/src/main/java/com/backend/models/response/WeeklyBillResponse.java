package com.backend.models.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class WeeklyBillResponse {
    private Map<LocalDate, BigDecimal> weeklyBill;

    public WeeklyBillResponse(Map<LocalDate, BigDecimal> weeklyBill){
        this.weeklyBill = weeklyBill;
    }

    public Map<LocalDate, BigDecimal> getWeeklyBill() {
        return weeklyBill;
    }

    public void setWeeklyBill(Map<LocalDate, BigDecimal> weeklyBill) {
        this.weeklyBill = weeklyBill;
    }
}
