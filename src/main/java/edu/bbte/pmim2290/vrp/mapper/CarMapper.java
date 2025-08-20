package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InCarDTO;
import edu.bbte.pmim2290.vrp.dto.OutCarDTO;
import edu.bbte.pmim2290.vrp.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(source = "depot.id", target = "depotId")
    @Mapping(source = "depot.name", target = "depotName")
    OutCarDTO toOutCarDTO(Car car);

    @Mapping(target = "id", ignore = true)
    Car toCar(InCarDTO inCarDTO);
}
