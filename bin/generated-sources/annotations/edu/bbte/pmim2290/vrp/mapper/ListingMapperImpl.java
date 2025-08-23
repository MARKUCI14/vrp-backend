package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InListingDTO;
import edu.bbte.pmim2290.vrp.dto.OutListingDTO;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.Listing;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-23T19:47:30+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ListingMapperImpl implements ListingMapper {

    @Override
    public OutListingDTO toOutListingDTO(Listing listing) {
        if ( listing == null ) {
            return null;
        }

        OutListingDTO outListingDTO = new OutListingDTO();

        outListingDTO.setDepotId( listingDepotId( listing ) );
        outListingDTO.setDepotName( listingDepotName( listing ) );
        outListingDTO.setId( listing.getId() );
        outListingDTO.setDate( listing.getDate() );

        return outListingDTO;
    }

    @Override
    public Listing toListing(InListingDTO inListingDTO) {
        if ( inListingDTO == null ) {
            return null;
        }

        Listing listing = new Listing();

        listing.setDepot( inListingDTO.getDepot() );
        listing.setDate( inListingDTO.getDate() );

        return listing;
    }

    @Override
    public void updateFromDTO(InListingDTO inListingDTO, Listing listing) {
        if ( inListingDTO == null ) {
            return;
        }

        listing.setDate( inListingDTO.getDate() );
    }

    private Long listingDepotId(Listing listing) {
        Depot depot = listing.getDepot();
        if ( depot == null ) {
            return null;
        }
        return depot.getId();
    }

    private String listingDepotName(Listing listing) {
        Depot depot = listing.getDepot();
        if ( depot == null ) {
            return null;
        }
        return depot.getName();
    }
}
