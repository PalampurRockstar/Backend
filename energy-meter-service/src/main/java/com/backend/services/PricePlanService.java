package com.backend.services;

import com.backend.models.ElectricityReading;
import com.backend.models.PricePlan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PricePlanService {
    public final static String PRICE_PLAN_ID_KEY = "pricePlanId";
    public final static String PRICE_PLAN_COMPARISONS_KEY = "pricePlanComparisons";

    private final List<PricePlan> pricePlans;
    private final MeterReadingService meterReadingService;
    private final Map<String, String> getPricePlanIdForSmartMeterId;

    public PricePlanService(List<PricePlan> pricePlans, MeterReadingService meterReadingService, Map<String, String> getPricePlanIdForSmartMeterId) {
        this.pricePlans = pricePlans;
        this.meterReadingService = meterReadingService;
        this.getPricePlanIdForSmartMeterId = getPricePlanIdForSmartMeterId;
    }

    public ResponseEntity comparisonsOfPricePlansWithInputedMeterPricePlan(String smartMeterId) {
        String pricePlanIdOfMeter = getPricePlanIdForSmartMeterId.get(smartMeterId);
        Optional<Map<String,BigDecimal>> consumptionsForPricePlans = getConsumptionOfElectricityReadingsforEachPricePlan(smartMeterId);
        if (consumptionsForPricePlans.isPresent()) {
            Map<String, Object> pricePlanComparisons = new HashMap<>();

            pricePlanComparisons.put(PRICE_PLAN_ID_KEY, pricePlanIdOfMeter);
            pricePlanComparisons.put(PRICE_PLAN_COMPARISONS_KEY, consumptionsForPricePlans.get());
            return ResponseEntity.ok(pricePlanComparisons);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Optional<Map<String,BigDecimal>> getConsumptionOfElectricityReadingsforEachPricePlan(String smartMeterId){
        Optional<List<ElectricityReading>> electricityReadings = meterReadingService.getMeterReadings(smartMeterId);
        if(!electricityReadings.isPresent()){
            return Optional.empty();
        }
        Optional<Map<String, BigDecimal>> consumptionsForPricePlans = Optional.of(pricePlans.stream().collect(
                Collectors.toMap(PricePlan::getPlanName, t -> calculateCost(electricityReadings.get(), t))));
            return consumptionsForPricePlans;

    }

    public ResponseEntity recommendCheapestPlan(String smartMeterId,Integer limit){
        Optional<Map<String,BigDecimal>> consumptionForPricePlan = getConsumptionOfElectricityReadingsforEachPricePlan(smartMeterId);
        if(!consumptionForPricePlan.isPresent()){
            return ResponseEntity.notFound().build();
        }
            List<Map.Entry<String, BigDecimal>> recommendations = new ArrayList<>(consumptionForPricePlan.get().entrySet());
            recommendations.sort(Comparator.comparing(Map.Entry::getValue));

            if (limit != null && limit < recommendations.size()) {
                recommendations = recommendations.subList(0, limit);
            }

            return ResponseEntity.ok(recommendations);
    }

    private BigDecimal calculateCost(List<ElectricityReading> electricityReadings, PricePlan pricePlan) {
        BigDecimal average = calculateAverageReading(electricityReadings);
        BigDecimal timeElapsed = calculateTimeElapsed(electricityReadings);

        BigDecimal averagedCost = average.divide(timeElapsed, RoundingMode.HALF_UP);
        return averagedCost.multiply(pricePlan.getUnitRate());
    }

    private BigDecimal calculateAverageReading(List<ElectricityReading> electricityReadings) {
        BigDecimal summedReadings = electricityReadings.stream()
                .map(ElectricityReading::getReading)
                .reduce(BigDecimal.ZERO, (reading, accumulator) -> reading.add(accumulator));

        return summedReadings.divide(BigDecimal.valueOf(electricityReadings.size()), RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTimeElapsed(List<ElectricityReading> electricityReadings) {
        ElectricityReading first = electricityReadings.stream()
                .min(Comparator.comparing(ElectricityReading::getTime))
                .get();
        ElectricityReading last = electricityReadings.stream()
                .max(Comparator.comparing(ElectricityReading::getTime))
                .get();

        return BigDecimal.valueOf(Duration.between(first.getTime(), last.getTime()).getSeconds() / 3600.0);
    }

}
