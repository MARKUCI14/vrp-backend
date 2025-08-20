package edu.bbte.pmim2290.vrp.dto;

import edu.bbte.pmim2290.vrp.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.locationtech.jts.geom.Point;

public class InDepotDTO {
    private User user;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 255)
    private String address;

    private Point location;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
