package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InPackageDTO;
import edu.bbte.pmim2290.vrp.dto.OutPackageDTO;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.Package;
import javax.annotation.processing.Generated;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-23T19:47:30+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class PackageMapperImpl implements PackageMapper {

    @Override
    public OutPackageDTO toOutPackageDTO(Package pkg) {
        if ( pkg == null ) {
            return null;
        }

        OutPackageDTO outPackageDTO = new OutPackageDTO();

        outPackageDTO.setDepotId( pkgDepotId( pkg ) );
        outPackageDTO.setDepotName( pkgDepotName( pkg ) );
        outPackageDTO.setLatitude( pkgLocationY( pkg ) );
        outPackageDTO.setLongitude( pkgLocationX( pkg ) );
        outPackageDTO.setId( pkg.getId() );
        outPackageDTO.setAddress( pkg.getAddress() );
        outPackageDTO.setWeight( pkg.getWeight() );
        outPackageDTO.setName( pkg.getName() );
        outPackageDTO.setPhoneNumber( pkg.getPhoneNumber() );
        outPackageDTO.setDeliveryDate( pkg.getDeliveryDate() );

        return outPackageDTO;
    }

    @Override
    public Package toPackage(InPackageDTO inPackageDTO) {
        if ( inPackageDTO == null ) {
            return null;
        }

        Package package1 = new Package();

        package1.setDepot( inPackageDTO.getDepot() );
        package1.setAddress( inPackageDTO.getAddress() );
        package1.setWeight( inPackageDTO.getWeight() );
        package1.setName( inPackageDTO.getName() );
        package1.setPhoneNumber( inPackageDTO.getPhoneNumber() );

        package1.setLocation( new org.locationtech.jts.geom.GeometryFactory().createPoint(new org.locationtech.jts.geom.Coordinate(inPackageDTO.getLongitude(), inPackageDTO.getLatitude())) );

        return package1;
    }

    @Override
    public void updateFromDTO(InPackageDTO inPackageDTO, Package pkg) {
        if ( inPackageDTO == null ) {
            return;
        }

        pkg.setAddress( inPackageDTO.getAddress() );
        pkg.setWeight( inPackageDTO.getWeight() );
        pkg.setName( inPackageDTO.getName() );
        pkg.setPhoneNumber( inPackageDTO.getPhoneNumber() );

        pkg.setLocation( new org.locationtech.jts.geom.GeometryFactory().createPoint(new org.locationtech.jts.geom.Coordinate(inPackageDTO.getLongitude(), inPackageDTO.getLatitude())) );
    }

    private Long pkgDepotId(Package package1) {
        Depot depot = package1.getDepot();
        if ( depot == null ) {
            return null;
        }
        return depot.getId();
    }

    private String pkgDepotName(Package package1) {
        Depot depot = package1.getDepot();
        if ( depot == null ) {
            return null;
        }
        return depot.getName();
    }

    private Double pkgLocationY(Package package1) {
        Point location = package1.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getY();
    }

    private Double pkgLocationX(Package package1) {
        Point location = package1.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getX();
    }
}
