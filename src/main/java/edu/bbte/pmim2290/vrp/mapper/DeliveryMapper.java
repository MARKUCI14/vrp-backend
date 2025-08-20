package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InDeliveryDTO;
import edu.bbte.pmim2290.vrp.dto.OutDeliveryDTO;
import edu.bbte.pmim2290.vrp.model.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    @Mapping(source = "listing.id", target = "listingId")
    @Mapping(source = "pkg.id", target = "packageId")
    @Mapping(source = "car.id", target = "carId")
    OutDeliveryDTO toOutDeliveryDTO(Delivery delivery);

    @Mapping(target = "id", ignore = true)
    Delivery toDelivery(InDeliveryDTO inDeliveryDTO);
}
