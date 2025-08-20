package edu.bbte.pmim2290.vrp.dto;

import java.time.LocalDateTime;

public class OutListingDTO {
    private Long id;

    private Long depotId;

    private Long depotName;

    private LocalDateTime time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepotId() {
        return depotId;
    }

    public void setDepotId(Long depotId) {
        this.depotId = depotId;
    }

    public Long getDepotName() {
        return depotName;
    }

    public void setDepotName(Long depotName) {
        this.depotName = depotName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
