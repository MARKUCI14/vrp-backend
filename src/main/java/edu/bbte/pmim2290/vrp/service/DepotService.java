package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Depot;

import java.util.List;
import java.util.Optional;

public interface DepotService {
    Depot createDepot(Depot depot) throws EntityNotFoundException, DatabaseException;

    Optional<Depot> getDepotById(Long id) throws EntityNotFoundException, DatabaseException;

    Depot updateDepot(Depot depot) throws EntityNotFoundException, DatabaseException;

    void deleteDepot(Long id) throws EntityNotFoundException, DatabaseException;

    List<Depot> getAllDepots() throws DatabaseException;
}
