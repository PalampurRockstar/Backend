package com.backend.services;

import com.backend.models.ElectricityReading;
import com.backend.models.MeterReadings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Service
public class MeterReadingService {

    public final Map<String, List<ElectricityReading>> meterReadingStorage;

    //constructor
    public MeterReadingService(Map<String, List<ElectricityReading>> meterReadingStorage) {
        this.meterReadingStorage = meterReadingStorage;
    }

    public ResponseEntity storeMeterReading(MeterReadings meterReadings) {
        String smartMeterId = meterReadings.getSmartMeterId();

        List<ElectricityReading> electricityReadingList = meterReadings.getElectricityReadings();
        if (smartMeterId != null && !smartMeterId.isEmpty() && electricityReadingList != null && !electricityReadingList.isEmpty()) {
            meterReadingStorage.get(smartMeterId).addAll(electricityReadingList);
            return ResponseEntity.ok(meterReadingStorage);
        } else {
            meterReadingStorage.put(smartMeterId, new ArrayList<>());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public Optional<List<ElectricityReading>> getMeterReadings(String smartMeterId){
        return Optional.ofNullable(meterReadingStorage.get(smartMeterId));
    }
}
