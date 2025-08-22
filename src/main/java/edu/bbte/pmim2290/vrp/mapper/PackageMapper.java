package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InPackageDTO;
import edu.bbte.pmim2290.vrp.dto.OutPackageDTO;
import edu.bbte.pmim2290.vrp.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PackageMapper {
    @Mapping(source = "depot.id", target = "depotId")
    @Mapping(source = "depot.name", target = "depotName")
    @Mapping(source = "location.y", target = "latitude") // JTS Point y = latitude
    @Mapping(source = "location.x", target = "longitude") // JTS Point x = longitude
    OutPackageDTO toOutPackageDTO(Package pkg);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location",
            expression = "java(new org.locationtech.jts.geom"
                    + ".GeometryFactory().createPoint(new org.locationtech.jts.geom"
                    + ".Coordinate(inPackageDTO.getLongitude(), inPackageDTO.getLatitude())))")
    Package toPackage(InPackageDTO inPackageDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "depot", ignore = true)
    @Mapping(target = "location",
            expression = "java(new org.locationtech.jts.geom.GeometryFactory()"
                    + ".createPoint(new org.locationtech.jts.geom.Coordinate("
                    + "inPackageDTO.getLongitude(), inPackageDTO.getLatitude())))")
    void updateFromDTO(InPackageDTO inPackageDTO, @MappingTarget Package pkg);
}
