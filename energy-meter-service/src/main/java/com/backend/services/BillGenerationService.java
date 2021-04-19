package com.backend.services;

import com.backend.models.ElectricityReading;
import com.backend.models.MeterReadings;
import com.backend.models.request.WeeklyBillRequest;
import com.backend.models.response.WeeklyBillResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface BillGenerationService {
    WeeklyBillResponse getWeeklyBill(WeeklyBillRequest weeklyBillRequest);
}
