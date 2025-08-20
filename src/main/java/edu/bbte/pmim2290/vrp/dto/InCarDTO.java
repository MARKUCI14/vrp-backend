package edu.bbte.pmim2290.vrp.dto;

import edu.bbte.pmim2290.vrp.model.Depot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InCarDTO {
    private Depot depot;

    @NotBlank(message = "VIN is required")
    @Size(min = 1, max = 255)
    private String vin;

    @NotBlank(message = "Make is required")
    @Size(min = 1, max = 255)
    private String make;

    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 255)
    private String model;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "Consumption is required")
    private Double consumption;

    @NotNull(message = "Max weight is required")
    private Double maxWeight;

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }
}
