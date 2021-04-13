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

    @GetMapping("/compare-all/{smartMeterId}")
    public ResponseEntity calculatedCostForEachPricePlan(@PathVariable String smartMeterId) {
       return pricePlanService.comparisonsOfPricePlansWithInputedMeterPricePlan(smartMeterId);
    }

    @GetMapping("/recommend/{smartMeterId}")
    public ResponseEntity<List<Map.Entry<String, BigDecimal>>> recommendCheapestPricePlans(@PathVariable String smartMeterId,
                                                                                           @RequestParam(value = "limit", required = false) Integer limit) {
            return pricePlanService.recommendCheapestPlan(smartMeterId,limit);
    }
}
