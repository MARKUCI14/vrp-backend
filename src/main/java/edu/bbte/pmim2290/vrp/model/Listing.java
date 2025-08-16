package edu.bbte.pmim2290.vrp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "listings", uniqueConstraints = {@UniqueConstraint(columnNames = {"depo_id", "date"})})
public class Listing extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "depo_id")
    private Depot depot;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Delivery> deliveries = new HashSet<>();


}