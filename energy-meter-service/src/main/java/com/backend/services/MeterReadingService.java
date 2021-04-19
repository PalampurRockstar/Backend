package com.backend.services;

import com.backend.models.ElectricityReading;
import com.backend.models.MeterReadings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        if(smartMeterId.isEmpty() && smartMeterId == null){
           return Optional.empty();
        }
        return Optional.ofNullable(meterReadingStorage.get(smartMeterId));
    }

    public void weeklyMeterReadingGenerate(){

    }
}
