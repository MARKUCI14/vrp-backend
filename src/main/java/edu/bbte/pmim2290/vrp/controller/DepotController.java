package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.dto.InDepotDTO;
import edu.bbte.pmim2290.vrp.dto.OutDepotDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.DepotMapper;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.service.DepotService;
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
@RequestMapping("/api/depots")
public class DepotController {
    @Autowired
    private final DepotService depotService;

    @Autowired
    private DepotMapper depotMapper;

    public DepotController(DepotService depotService) {
        this.depotService = depotService;
    }

    @GetMapping
    public List<OutDepotDTO> getDepots()
            throws DatabaseException {
        List<Depot> depots;
        depots = depotService.getAllDepots();


        return depots.stream()
                .map(depotMapper::toOutDepotDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutDepotDTO getDepot(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Depot> depot = depotService.getDepotById(id);
        return depotMapper.toOutDepotDTO(depot.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OutDepotDTO> createDepot(@Valid @RequestBody InDepotDTO inDepot)
            throws DatabaseException, EntityNotFoundException {
        Depot depot = depotMapper.toDepot(inDepot);
        URI uri = URI.create("api/Depots/" + depot.getId());
        return ResponseEntity.created(uri).body(depotMapper.toOutDepotDTO(depotService.createDepot(depot)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutDepotDTO> updateDepot(@PathVariable Long id, @Valid @RequestBody InDepotDTO inDepot)
            throws EntityNotFoundException, DatabaseException {
        Depot depot = depotMapper.toDepot(inDepot);
        depot.setId(id);
        return ResponseEntity.ok(depotMapper.toOutDepotDTO(depotService.updateDepot(depot)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepot(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Depot> depot = depotService.getDepotById(id);
        if (depot.isEmpty()) {
            throw new EntityNotFoundException("The requested Depot does not exist");
        }

        depotService.deleteDepot(id);
    }
}
