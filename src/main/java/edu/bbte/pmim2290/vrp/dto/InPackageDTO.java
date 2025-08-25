package edu.bbte.pmim2290.vrp.dto;

import edu.bbte.pmim2290.vrp.model.Depot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class InPackageDTO {
    private Depot depot;

    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 255)
    private String address;

    private Double latitude;

    private Double longitude;

    @NotNull(message = "Weight is required")
    private Double weight;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank(message = "Phone number is required")
    @Size(min = 1, max = 255)
    private String phoneNumber;

    @NotNull(message = "Date is required")
    private LocalDate deliveryDate;

    public Depot getDepot() {

        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
