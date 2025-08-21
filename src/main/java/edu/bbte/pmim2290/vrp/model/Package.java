package edu.bbte.pmim2290.vrp.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "packages")
public class Package extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;

    private String address;

    @Column(columnDefinition = "GEOGRAPHY(Point, 4326)", nullable = false)
    private Point location;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "delivery_date")
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Package)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Package p = (Package) o;
        return Objects.equals(depot, p.depot)
                && Objects.equals(name, p.name)
                && Objects.equals(location, p.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), depot, name, location);
    }

}
