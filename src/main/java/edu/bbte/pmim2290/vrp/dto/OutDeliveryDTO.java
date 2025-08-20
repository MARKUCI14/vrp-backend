package edu.bbte.pmim2290.vrp.dto;

public class OutDeliveryDTO {
    private Long id;

    private Long listingId;

    private Long packageId;

    private Long carId;

    private Integer deliveryOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Integer getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(Integer deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
