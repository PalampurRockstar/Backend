package com.backend.generator;

import com.backend.models.ElectricityReading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

public class ElectricityReadingGenerator {
    public List<ElectricityReading> generate(int number) {
        List<ElectricityReading> readings = new ArrayList<>();
        Instant now = Instant.now();
        Random readingRandomiser = new Random();

        for (int i = 0; i < number; i++) {
            double positiveRandomValue = Math.abs(readingRandomiser.nextGaussian());
            BigDecimal randomReading = BigDecimal.valueOf(positiveRandomValue).setScale(4, RoundingMode.CEILING);
            ElectricityReading electricityReading = new ElectricityReading(now.minusSeconds(i * 10), randomReading);
            readings.add(electricityReading);

        }
        readings.sort(Comparator.comparing(ElectricityReading::getTime));
        return readings;
    }

}
