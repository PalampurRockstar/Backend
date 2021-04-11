package com.backend.models;


import java.math.BigDecimal;

public class PricePlan {
    private String energySupplier;
    private String planName;
    private BigDecimal unitRate;

    public PricePlan(String energySupplier, String planName, BigDecimal unitRate) {
        this.energySupplier = energySupplier;
        this.planName = planName;
        this.unitRate = unitRate;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getEnergySupplier() {
        return energySupplier;
    }

    public void setEnergySupplier(String energySupplier) {
        this.energySupplier = energySupplier;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(BigDecimal unitRate) {
        this.unitRate = unitRate;
    }
}
