package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.dto.InDeliveryDTO;
import edu.bbte.pmim2290.vrp.dto.OutDeliveryDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.DeliveryMapper;
import edu.bbte.pmim2290.vrp.model.Delivery;
import edu.bbte.pmim2290.vrp.service.DeliveryService;
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
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/deliveries")
public class DeliveryController {
    @Autowired
    private final DeliveryService deliveryService;

    @Autowired
    private DeliveryMapper deliveryMapper;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<OutDeliveryDTO> getDeliverys()
            throws DatabaseException {
        List<Delivery> deliverys;
        deliverys = deliveryService.getAllDeliverys();


        return deliverys.stream()
                .map(deliveryMapper::toOutDeliveryDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutDeliveryDTO getDelivery(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Delivery> delivery = deliveryService.getDeliveryById(id);
        return deliveryMapper.toOutDeliveryDTO(delivery.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OutDeliveryDTO> createDelivery(@Valid @RequestBody InDeliveryDTO inDelivery)
            throws DatabaseException, EntityNotFoundException {
        Delivery delivery = deliveryMapper.toDelivery(inDelivery);
        URI uri = URI.create("api/Deliverys/" + delivery.getId());
        return ResponseEntity.created(uri)
                .body(deliveryMapper.toOutDeliveryDTO(deliveryService.createDelivery(delivery)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutDeliveryDTO> updateDelivery(@PathVariable Long id,
                                                         @Valid @RequestBody InDeliveryDTO inDelivery)
            throws EntityNotFoundException, DatabaseException {
        Delivery delivery = deliveryMapper.toDelivery(inDelivery);
        delivery.setId(id);
        return ResponseEntity.ok(deliveryMapper.toOutDeliveryDTO(deliveryService.updateDelivery(delivery)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDelivery(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Delivery> delivery = deliveryService.getDeliveryById(id);
        if (delivery.isEmpty()) {
            throw new EntityNotFoundException("The requested Delivery does not exist");
        }

        deliveryService.deleteDelivery(id);
    }
}

