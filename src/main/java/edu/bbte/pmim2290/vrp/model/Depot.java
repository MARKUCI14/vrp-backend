package edu.bbte.pmim2290.vrp.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "depots")
public class Depot extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String address;

    @Column(columnDefinition = "GEOGRAPHY(Point, 4326)", nullable = false)
    private Point location;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Car> cars = new HashSet<>();

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Package> packages = new HashSet<>();

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Listing> listings = new HashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public Set<Listing> getListings() {
        return listings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depot)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Depot depot = (Depot) o;
        return Objects.equals(name, depot.name)
                && Objects.equals(location, depot.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, location);
    }

}
