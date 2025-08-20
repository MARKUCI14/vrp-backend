package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InListingDTO;
import edu.bbte.pmim2290.vrp.dto.OutListingDTO;
import edu.bbte.pmim2290.vrp.model.Listing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ListingMapper {
    @Mapping(source = "depot.id", target = "depotId")
    @Mapping(source = "depot.name", target = "depotName")
    OutListingDTO toOutListingDTO(Listing listing);

    @Mapping(target = "id", ignore = true)
    Listing toListing(InListingDTO inListingDTO);
}
