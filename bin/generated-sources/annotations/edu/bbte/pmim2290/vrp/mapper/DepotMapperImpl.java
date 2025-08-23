package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InDepotDTO;
import edu.bbte.pmim2290.vrp.dto.OutDepotDTO;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.User;
import javax.annotation.processing.Generated;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-23T19:47:30+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class DepotMapperImpl implements DepotMapper {

    @Override
    public OutDepotDTO toOutDepotDTO(Depot depot) {
        if ( depot == null ) {
            return null;
        }

        OutDepotDTO outDepotDTO = new OutDepotDTO();

        outDepotDTO.setUserId( depotUserId( depot ) );
        outDepotDTO.setUsername( depotUserUsername( depot ) );
        outDepotDTO.setLatitude( depotLocationY( depot ) );
        outDepotDTO.setLongitude( depotLocationX( depot ) );
        outDepotDTO.setId( depot.getId() );
        outDepotDTO.setName( depot.getName() );
        outDepotDTO.setAddress( depot.getAddress() );

        return outDepotDTO;
    }

    @Override
    public Depot toDepot(InDepotDTO inDepotDTO) {
        if ( inDepotDTO == null ) {
            return null;
        }

        Depot depot = new Depot();

        depot.setUser( inDepotDTO.getUser() );
        depot.setAddress( inDepotDTO.getAddress() );
        depot.setName( inDepotDTO.getName() );

        depot.setLocation( new org.locationtech.jts.geom.GeometryFactory().createPoint(new org.locationtech.jts.geom.Coordinate(inDepotDTO.getLongitude(), inDepotDTO.getLatitude())) );

        return depot;
    }

    @Override
    public void updateFromDTO(InDepotDTO inDepotDTO, Depot depot) {
        if ( inDepotDTO == null ) {
            return;
        }

        depot.setAddress( inDepotDTO.getAddress() );
        depot.setName( inDepotDTO.getName() );

        depot.setLocation( new org.locationtech.jts.geom.GeometryFactory().createPoint(new org.locationtech.jts.geom.Coordinate(inDepotDTO.getLongitude(), inDepotDTO.getLatitude())) );
    }

    private Long depotUserId(Depot depot) {
        User user = depot.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String depotUserUsername(Depot depot) {
        User user = depot.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUsername();
    }

    private Double depotLocationY(Depot depot) {
        Point location = depot.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getY();
    }

    private Double depotLocationX(Depot depot) {
        Point location = depot.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getX();
    }
}
