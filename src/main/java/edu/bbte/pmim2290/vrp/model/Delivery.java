package edu.bbte.pmim2290.vrp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery", uniqueConstraints = { @UniqueConstraint(columnNames = {"listing_id", "package_id"}) })
public class Delivery extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package pkg;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "delivery_order", nullable = false)
    private Integer deliveryOrder;

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Package getPkg() {
        return pkg;
    }

    public void setPkg(Package pkg) {
        this.pkg = pkg;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(Integer deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }
}
