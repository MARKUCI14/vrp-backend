package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dao.DeliveryDAO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImplements implements DeliveryService {
    @Autowired
    private final DeliveryDAO deliveryDAO;

    public DeliveryServiceImplements(DeliveryDAO deliveryDAO) {
        this.deliveryDAO = deliveryDAO;
    }

    @Override
    public Delivery createDelivery(Delivery delivery) throws EntityNotFoundException, DatabaseException {
        return deliveryDAO.save(delivery);
    }

    @Override
    public Optional<Delivery> getDeliveryById(Long id) throws EntityNotFoundException, DatabaseException {
        return deliveryDAO.findById(id);
    }

    @Override
    public Delivery updateDelivery(Delivery delivery) throws EntityNotFoundException, DatabaseException {
        return deliveryDAO.save(delivery);
    }

    @Override
    public void deleteDelivery(Long id) throws EntityNotFoundException, DatabaseException {
        deliveryDAO.deleteById(id);
    }

    @Override
    public List<Delivery> getAllDeliverys() throws DatabaseException {
        return deliveryDAO.findAll();
    }
}
