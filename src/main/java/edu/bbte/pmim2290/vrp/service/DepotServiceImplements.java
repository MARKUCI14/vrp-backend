package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dao.DepotDAO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Depot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepotServiceImplements implements DepotService {
    @Autowired
    private final DepotDAO depotDAO;

    public DepotServiceImplements(DepotDAO depotDAO) {
        this.depotDAO = depotDAO;
    }

    @Override
    public Depot createDepot(Depot depot) throws EntityNotFoundException, DatabaseException {
        return depotDAO.save(depot);
    }

    @Override
    public Optional<Depot> getDepotById(Long id) throws EntityNotFoundException, DatabaseException {
        return depotDAO.findById(id);
    }

    @Override
    public Depot updateDepot(Depot depot) throws EntityNotFoundException, DatabaseException {
        return depotDAO.save(depot);
    }

    @Override
    public void deleteDepot(Long id) throws EntityNotFoundException, DatabaseException {
        depotDAO.deleteById(id);
    }

    @Override
    public List<Depot> getAllDepots() throws DatabaseException {
        return depotDAO.findAll();
    }

    @Override
    public List<Depot> getDepotsForUser(Long id) throws DatabaseException {
        return depotDAO.findByUserId(id);
    }
}
