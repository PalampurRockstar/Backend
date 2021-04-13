package com.backend.models;

import java.util.List;

public class MeterReadings {
    private String smartMeterId;
    private List<ElectricityReading> electricityReadings;

    public MeterReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
        this.smartMeterId = smartMeterId;
        this.electricityReadings = electricityReadings;
    }

    public String getSmartMeterId() {
        return smartMeterId;
    }

    public void setSmartMeterId(String smartMeterId) {
        this.smartMeterId = smartMeterId;
    }

    public List<ElectricityReading> getElectricityReadings() {
        return electricityReadings;
    }

    public void setElectricityReadings(List<ElectricityReading> electricityReadings) {
        this.electricityReadings = electricityReadings;
    }
}
