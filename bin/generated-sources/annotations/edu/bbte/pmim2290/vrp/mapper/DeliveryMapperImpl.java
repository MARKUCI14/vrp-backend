package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InDeliveryDTO;
import edu.bbte.pmim2290.vrp.dto.OutDeliveryDTO;
import edu.bbte.pmim2290.vrp.model.Car;
import edu.bbte.pmim2290.vrp.model.Delivery;
import edu.bbte.pmim2290.vrp.model.Listing;
import edu.bbte.pmim2290.vrp.model.Package;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-23T19:47:30+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class DeliveryMapperImpl implements DeliveryMapper {

    @Override
    public OutDeliveryDTO toOutDeliveryDTO(Delivery delivery) {
        if ( delivery == null ) {
            return null;
        }

        OutDeliveryDTO outDeliveryDTO = new OutDeliveryDTO();

        outDeliveryDTO.setListingId( deliveryListingId( delivery ) );
        outDeliveryDTO.setPackageId( deliveryPkgId( delivery ) );
        outDeliveryDTO.setCarId( deliveryCarId( delivery ) );
        outDeliveryDTO.setId( delivery.getId() );
        outDeliveryDTO.setDeliveryOrder( delivery.getDeliveryOrder() );

        return outDeliveryDTO;
    }

    @Override
    public Delivery toDelivery(InDeliveryDTO inDeliveryDTO) {
        if ( inDeliveryDTO == null ) {
            return null;
        }

        Delivery delivery = new Delivery();

        delivery.setListing( inDeliveryDTO.getListing() );
        delivery.setPkg( inDeliveryDTO.getPkg() );
        delivery.setCar( inDeliveryDTO.getCar() );
        delivery.setDeliveryOrder( inDeliveryDTO.getDeliveryOrder() );

        return delivery;
    }

    private Long deliveryListingId(Delivery delivery) {
        Listing listing = delivery.getListing();
        if ( listing == null ) {
            return null;
        }
        return listing.getId();
    }

    private Long deliveryPkgId(Delivery delivery) {
        Package pkg = delivery.getPkg();
        if ( pkg == null ) {
            return null;
        }
        return pkg.getId();
    }

    private Long deliveryCarId(Delivery delivery) {
        Car car = delivery.getCar();
        if ( car == null ) {
            return null;
        }
        return car.getId();
    }
}
