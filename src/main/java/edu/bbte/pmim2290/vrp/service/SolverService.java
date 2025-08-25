package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dto.OutDeliveryDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;

import java.util.List;

public interface SolverService {
    List<OutDeliveryDTO> solveAndPersistListing(Long depotId, Long listingId)
            throws EntityNotFoundException, DatabaseException;

    List<OutDeliveryDTO> resolve(Long depotId, Long listingId)
            throws EntityNotFoundException, DatabaseException;
}
