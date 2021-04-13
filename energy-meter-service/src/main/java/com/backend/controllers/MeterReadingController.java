package com.backend.controllers;

import com.backend.models.ElectricityReading;
import com.backend.models.MeterReadings;
import com.backend.services.MeterReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/readings")
public class MeterReadingController {


    @Autowired
    MeterReadingService meterReadingService;

    @PostMapping("/store")
    public ResponseEntity storeReadings(@RequestBody MeterReadings meterReadings) {
        return meterReadingService.storeMeterReading(meterReadings);
    }
    @GetMapping("/read/{smartMeterId}")
    public Optional<List<ElectricityReading>> getReadings(@PathVariable String smartMeterId){
        return meterReadingService.getMeterReadings(smartMeterId);
    }
}
