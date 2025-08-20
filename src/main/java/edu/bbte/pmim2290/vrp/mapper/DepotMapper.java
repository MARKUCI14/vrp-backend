package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InDepotDTO;
import edu.bbte.pmim2290.vrp.dto.OutDepotDTO;
import edu.bbte.pmim2290.vrp.model.Depot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepotMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    OutDepotDTO toOutDepotDTO(Depot depot);

    @Mapping(target = "id", ignore = true)
    Depot toDepot(InDepotDTO inDepotDTO);
}
