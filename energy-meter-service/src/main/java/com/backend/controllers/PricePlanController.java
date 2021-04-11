package com.backend.controllers;

import com.backend.services.PricePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/price-plans")
public class PricePlanController {

    @Autowired
    PricePlanService pricePlanService;

//    private final PricePlanService pricePlanService;

//    public PricePlanController(PricePlanService pricePlanService) {
//        this.pricePlanService = pricePlanService;
//    }

    @GetMapping("/compare-all/{smartMeterId}")
    public ResponseEntity calculatedCostForEachPricePlan(@PathVariable String smartMeterId) {
       return pricePlanService.comparisonsOfPricePlansWithInputedMeterPricePlan(smartMeterId);
    }

    @GetMapping("/recommend/{smartMeterId}")
    public ResponseEntity<List<Map.Entry<String, BigDecimal>>> recommendCheapestPricePlans(@PathVariable String smartMeterId,
                                                                                           @RequestParam(value = "limit", required = false) Integer limit) {
            return pricePlanService.recommendCheapestPlan(smartMeterId,limit);
//        Optional<Map<String, BigDecimal>> consumptionsForPricePlans =
//                pricePlanService.getConsumptionOfElectricityReadingsforEachPricePlan(smartMeterId);
//
//        if (!consumptionsForPricePlans.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        List<Map.Entry<String, BigDecimal>> recommendations = new ArrayList<>(consumptionsForPricePlans.get().entrySet());
//        recommendations.sort(Comparator.comparing(Map.Entry::getValue));
//
//        if (limit != null && limit < recommendations.size()) {
//            recommendations = recommendations.subList(0, limit);
//        }
//
//        return ResponseEntity.ok(recommendations);
    }
}
