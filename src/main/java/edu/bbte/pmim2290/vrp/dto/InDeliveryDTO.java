package edu.bbte.pmim2290.vrp.dto;

import edu.bbte.pmim2290.vrp.model.Car;
import edu.bbte.pmim2290.vrp.model.Listing;
import edu.bbte.pmim2290.vrp.model.Package;
import jakarta.validation.constraints.NotNull;

public class InDeliveryDTO {
    private Listing listing;

    private Package pkg;

    private Car car;

    @NotNull(message = "Delivery order is required")
    private int deliveryOrder;

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

    public int getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(int deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }
}
