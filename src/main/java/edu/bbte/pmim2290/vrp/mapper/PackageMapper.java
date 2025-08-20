package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InPackageDTO;
import edu.bbte.pmim2290.vrp.dto.OutPackageDTO;
import edu.bbte.pmim2290.vrp.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PackageMapper {
    @Mapping(source = "depot.id", target = "depotId")
    @Mapping(source = "depot.name", target = "depotName")
    OutPackageDTO toOutPackageDTO(Package pkg);

    @Mapping(target = "id", ignore = true)
    Package toPackage(InPackageDTO inPackageDTO);
}
