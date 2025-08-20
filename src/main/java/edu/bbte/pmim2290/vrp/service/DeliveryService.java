package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryService {
    Delivery createDelivery(Delivery delivery) throws EntityNotFoundException, DatabaseException;

    Optional<Delivery> getDeliveryById(Long id) throws EntityNotFoundException, DatabaseException;

    Delivery updateDelivery(Delivery delivery) throws EntityNotFoundException, DatabaseException;

    void deleteDelivery(Long id) throws EntityNotFoundException, DatabaseException;

    List<Delivery> getAllDeliverys() throws DatabaseException;
}
