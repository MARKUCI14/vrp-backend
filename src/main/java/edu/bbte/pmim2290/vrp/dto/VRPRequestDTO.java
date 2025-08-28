package edu.bbte.pmim2290.vrp.dto;

import java.util.List;

public class VRPRequestDTO {
    private OutDepotDTO depot;
    private List<OutPackageDTO> packages;
    private List<OutCarDTO> cars;

    public VRPRequestDTO(OutDepotDTO depot, List<OutPackageDTO> packages, List<OutCarDTO> cars) {
        this.depot = depot;
        this.packages = packages;
        this.cars = cars;
    }

    public OutDepotDTO getDepot() {
        return depot;
    }

    public void setDepot(OutDepotDTO depot) {
        this.depot = depot;
    }

    public List<OutPackageDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<OutPackageDTO> packages) {
        this.packages = packages;
    }

    public List<OutCarDTO> getCars() {
        return cars;
    }

    public void setCars(List<OutCarDTO> cars) {
        this.cars = cars;
    }
}
