package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.config.UserDetailsImpl;
import edu.bbte.pmim2290.vrp.dto.InDepotDTO;
import edu.bbte.pmim2290.vrp.dto.OutDepotDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.DepotMapper;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.User;
import edu.bbte.pmim2290.vrp.service.DepotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/depots")
public class DepotController {
    @Autowired
    private final DepotService depotService;

    @Autowired
    private DepotMapper depotMapper;

    public DepotController(DepotService depotService) {
        this.depotService = depotService;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }

    @GetMapping
    public List<OutDepotDTO> getDepots() throws DatabaseException {
        User user = getCurrentUser();
        List<Depot> depots;
        depots = depotService.getDepotsForUser(user.getId());

        return depots.stream()
                .map(depotMapper::toOutDepotDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutDepotDTO getDepot(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        User user = getCurrentUser();
        Depot depot = depotService.getDepotById(id)
                .orElseThrow(() -> new EntityNotFoundException("Depot not found"));

        if (!depot.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Access denied");
        }

        return depotMapper.toOutDepotDTO(depot);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OutDepotDTO> createDepot(@Valid @RequestBody InDepotDTO inDepot)
            throws DatabaseException, EntityNotFoundException {
        User user = getCurrentUser();

        Depot depot = depotMapper.toDepot(inDepot);
        depot.setUser(user); // force ownership

        Depot savedDepot = depotService.createDepot(depot);
        URI uri = URI.create("api/depots/" + savedDepot.getId());
        return ResponseEntity.created(uri).body(depotMapper.toOutDepotDTO(savedDepot));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutDepotDTO> updateDepot(@PathVariable Long id,
                                                   @Valid @RequestBody InDepotDTO inDepot)
            throws EntityNotFoundException, DatabaseException {
        User user = getCurrentUser();

        Depot depot = depotService.getDepotById(id)
                .orElseThrow(() -> new EntityNotFoundException("Depot not found"));

        if (!depot.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        depotMapper.updateFromDTO(inDepot, depot);
        Depot updatedDepot = depotService.updateDepot(depot);

        return ResponseEntity.ok(depotMapper.toOutDepotDTO(updatedDepot));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepot(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        User user = getCurrentUser();

        Depot depot = depotService.getDepotById(id)
                .orElseThrow(() -> new EntityNotFoundException("Depot not found"));

        if (!depot.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Access denied");
        }

        depotService.deleteDepot(id);
    }
}
