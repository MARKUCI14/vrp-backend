package edu.bbte.pmim2290.vrp.dto;

import edu.bbte.pmim2290.vrp.model.Depot;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class InListingDTO {
    private Depot depot;

    @NotNull(message = "Date is required")
    private LocalDate date;

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
