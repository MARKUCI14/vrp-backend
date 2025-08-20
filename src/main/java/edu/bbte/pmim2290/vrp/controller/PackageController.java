package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.dto.InPackageDTO;
import edu.bbte.pmim2290.vrp.dto.OutPackageDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.PackageMapper;
import edu.bbte.pmim2290.vrp.model.Package;
import edu.bbte.pmim2290.vrp.service.PackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/packages")
public class PackageController {
    @Autowired
    private final PackageService packageService;

    @Autowired
    private PackageMapper packageMapper;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public List<OutPackageDTO> getPackages()
            throws DatabaseException {
        List<Package> packages;
        packages = packageService.getAllPackages();

        return packages.stream()
                .map(packageMapper::toOutPackageDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutPackageDTO getPackage(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Package> pkg = packageService.getPackageById(id);
        return packageMapper.toOutPackageDTO(pkg.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OutPackageDTO> createPackage(@Valid @RequestBody InPackageDTO inPackage)
            throws DatabaseException, EntityNotFoundException {
        Package pkg = packageMapper.toPackage(inPackage);
        URI uri = URI.create("api/Packages/" + pkg.getId());
        return ResponseEntity.created(uri).body(packageMapper.toOutPackageDTO(packageService.createPackage(pkg)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutPackageDTO> updatePackage(@PathVariable Long id,
                                                       @Valid @RequestBody InPackageDTO inPackage)
            throws EntityNotFoundException, DatabaseException {
        Package pkg = packageMapper.toPackage(inPackage);
        pkg.setId(id);
        return ResponseEntity.ok(packageMapper.toOutPackageDTO(packageService.updatePackage(pkg)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePackage(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Package> pkg = packageService.getPackageById(id);
        if (pkg.isEmpty()) {
            throw new EntityNotFoundException("The requested Package does not exist");
        }

        packageService.deletePackage(id);
    }
}
