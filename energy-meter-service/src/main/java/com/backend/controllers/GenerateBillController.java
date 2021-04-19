package com.backend.controllers;

import com.backend.models.ElectricityReading;
import com.backend.models.MeterReadings;
import com.backend.models.request.WeeklyBillRequest;
import com.backend.models.response.WeeklyBillResponse;
import com.backend.services.BillGenerationService;
import com.backend.services.MeterReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/generate")
public class GenerateBillController {


    @Autowired
    BillGenerationService billGenerationService;

    @PostMapping("/weekly-bill")
    public ResponseEntity generateBill(@RequestBody WeeklyBillRequest weeklyBillRequest) {
        WeeklyBillResponse weeklyBill = billGenerationService.getWeeklyBill(weeklyBillRequest);
        return ResponseEntity.ok(weeklyBill);
    }
}
