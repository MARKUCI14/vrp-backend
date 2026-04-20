package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.config.UserDetailsImpl;
import edu.bbte.pmim2290.vrp.dto.InPackageDTO;
import edu.bbte.pmim2290.vrp.dto.OutPackageDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.PackageMapper;
import edu.bbte.pmim2290.vrp.model.Package;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.User;
import edu.bbte.pmim2290.vrp.service.PackageService;
import edu.bbte.pmim2290.vrp.service.DepotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/depots/{depotId}/packages")
public class DepotPackagesController {
    private static final String DEPOT_NOT_FOUND = "Depot not found";
    private static final String PACKAGE_NOT_FOUND = "Package not found";
    private static final String ACCESS_DENIED = "Access denied";

    private final DepotService depotService;
    private final PackageService packageService;

    @Autowired
    private PackageMapper packageMapper;

    public DepotPackagesController(DepotService depotService, PackageService packageService) {
        this.depotService = depotService;
        this.packageService = packageService;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }

    public boolean validateDepotId(@PathVariable Long depotId) throws EntityNotFoundException, DatabaseException {
        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException(DEPOT_NOT_FOUND));
        User user = getCurrentUser();

        return depot.getUser().getId().equals(user.getId());
    }

    @GetMapping
    public List<OutPackageDTO> getDepotsPackages(@PathVariable Long depotId,
            @RequestParam(required = false) String queryDate)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        List<Package> packages;
        if (queryDate != null && !queryDate.isBlank()) {
            LocalDate deliveryDate = LocalDate.parse(queryDate);
            packages = packageService.findByDepotIdAndDeliveryDate(depotId, deliveryDate);
        } else {
            packages = packageService.findByDepotId(depotId);
        }

        return packages.stream()
                .map(packageMapper::toOutPackageDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutPackageDTO getDepotsPackage(@PathVariable Long depotId, @PathVariable Long id)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        Package pkg = packageService.getPackageById(id)
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        if (!pkg.getDepot().getId().equals(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        return packageMapper.toOutPackageDTO(pkg);
    }

    @PostMapping
    public ResponseEntity<OutPackageDTO> addPackageToDepot(@PathVariable Long depotId,
            @Valid @RequestBody InPackageDTO inPackageDTO)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException(DEPOT_NOT_FOUND));

        Package pkg = packageMapper.toPackage(inPackageDTO);
        pkg.setDepot(depot);

        Package savedPackage = packageService.createPackage(pkg);
        URI uri = URI.create("/api/depots/" + depotId + "/packages/" + savedPackage.getId());
        return ResponseEntity.created(uri).body(packageMapper.toOutPackageDTO(savedPackage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutPackageDTO> updateDepotsPackage(@PathVariable Long depotId,
            @PathVariable Long id,
            @Valid @RequestBody InPackageDTO inPackageDTO)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }
        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException(DEPOT_NOT_FOUND));

        Package pkg = packageService.getPackageById(id)
                .orElseThrow(() -> new EntityNotFoundException(PACKAGE_NOT_FOUND));

        packageMapper.updateFromDTO(inPackageDTO, pkg);
        pkg.setDepot(depot);

        Package savedPackage = packageService.createPackage(pkg);
        return ResponseEntity.ok(packageMapper.toOutPackageDTO(savedPackage));
    }

    @DeleteMapping("/{id}")
    public void deleteDepotsPackage(@PathVariable Long depotId, @PathVariable Long id)
            throws EntityNotFoundException, DatabaseException {
        Optional<Depot> depotOptional = depotService.getDepotById(depotId);
        if (!depotOptional.isPresent()) {
            throw new EntityNotFoundException(DEPOT_NOT_FOUND);
        }

        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        Optional<Package> pkg = packageService.getPackageById(id);
        if (!pkg.isPresent()) {
            throw new EntityNotFoundException(PACKAGE_NOT_FOUND);
        }

        if (!pkg.get().getDepot().getId().equals(depotId)) {
            throw new EntityNotFoundException("Package does not belong to depot");
        }

        packageService.deletePackage(id);
    }
}
