package edu.bbte.pmim2290.vrp.dto;

import edu.bbte.pmim2290.vrp.model.Depot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

public class InPackageDTO {
    private Depot depot;

    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 255)
    private String address;

    @NotNull(message = "Location is required")
    private Point location;

    @NotNull(message = "Weight is required")
    private Double weight;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank(message = "Phone number is required")
    @Size(min = 1, max = 255)
    private String phoneNumber;

    @NotNull(message = "Date is required")
    private LocalDate date;

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

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
