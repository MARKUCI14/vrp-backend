package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InCarDTO;
import edu.bbte.pmim2290.vrp.dto.OutCarDTO;
import edu.bbte.pmim2290.vrp.model.Car;
import edu.bbte.pmim2290.vrp.model.Depot;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-23T19:47:30+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public OutCarDTO toOutCarDTO(Car car) {
        if ( car == null ) {
            return null;
        }

        OutCarDTO outCarDTO = new OutCarDTO();

        outCarDTO.setDepotId( carDepotId( car ) );
        outCarDTO.setDepotName( carDepotName( car ) );
        outCarDTO.setId( car.getId() );
        outCarDTO.setVin( car.getVin() );
        outCarDTO.setMake( car.getMake() );
        outCarDTO.setModel( car.getModel() );
        outCarDTO.setYear( car.getYear() );
        outCarDTO.setConsumption( car.getConsumption() );
        outCarDTO.setMaxWeight( car.getMaxWeight() );

        return outCarDTO;
    }

    @Override
    public Car toCar(InCarDTO inCarDTO) {
        if ( inCarDTO == null ) {
            return null;
        }

        Car car = new Car();

        car.setDepot( inCarDTO.getDepot() );
        car.setVin( inCarDTO.getVin() );
        car.setMake( inCarDTO.getMake() );
        car.setModel( inCarDTO.getModel() );
        car.setYear( inCarDTO.getYear() );
        car.setConsumption( inCarDTO.getConsumption() );
        car.setMaxWeight( inCarDTO.getMaxWeight() );

        return car;
    }

    @Override
    public void updateFromDTO(InCarDTO inCarDTO, Car car) {
        if ( inCarDTO == null ) {
            return;
        }

        car.setVin( inCarDTO.getVin() );
        car.setMake( inCarDTO.getMake() );
        car.setModel( inCarDTO.getModel() );
        car.setYear( inCarDTO.getYear() );
        car.setConsumption( inCarDTO.getConsumption() );
        car.setMaxWeight( inCarDTO.getMaxWeight() );
    }

    private Long carDepotId(Car car) {
        Depot depot = car.getDepot();
        if ( depot == null ) {
            return null;
        }
        return depot.getId();
    }

    private String carDepotName(Car car) {
        Depot depot = car.getDepot();
        if ( depot == null ) {
            return null;
        }
        return depot.getName();
    }
}
