package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InDepotDTO;
import edu.bbte.pmim2290.vrp.dto.OutDepotDTO;
import edu.bbte.pmim2290.vrp.model.Depot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepotMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "location.y", target = "latitude") // JTS Point y = latitude
    @Mapping(source = "location.x", target = "longitude") // JTS Point x = longitude
    OutDepotDTO toOutDepotDTO(Depot depot);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location",
            expression = "java(new org.locationtech.jts.geom"
                    + ".GeometryFactory().createPoint(new org.locationtech.jts.geom"
                    + ".Coordinate(inDepotDTO.getLongitude(), inDepotDTO.getLatitude())))")
    Depot toDepot(InDepotDTO inDepotDTO);

    @Mapping(target = "id", ignore = true)   // don’t overwrite ID
    @Mapping(target = "user", ignore = true) // don’t overwrite User
    @Mapping(target = "location",
            expression = "java(new org.locationtech.jts.geom.GeometryFactory()"
                    + ".createPoint(new org.locationtech.jts.geom.Coordinate("
                    + "inDepotDTO.getLongitude(), inDepotDTO.getLatitude())))")
    void updateFromDTO(InDepotDTO inDepotDTO, @MappingTarget Depot depot);
}
