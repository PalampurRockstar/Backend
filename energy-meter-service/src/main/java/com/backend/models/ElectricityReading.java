package com.backend.models;

import java.math.BigDecimal;
import java.time.Instant;

public class ElectricityReading {
    private Instant time;
    private BigDecimal reading;

    public ElectricityReading(Instant time, BigDecimal reading) {
        this.time = time;
        this.reading = reading;
    }

    public Instant getTime() {
        return time;
    }

    public BigDecimal getReading() {
        return reading;
    }

}
