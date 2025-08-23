package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InUserDTO;
import edu.bbte.pmim2290.vrp.dto.OutUserDTO;
import edu.bbte.pmim2290.vrp.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-23T19:47:30+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public OutUserDTO toOutUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        OutUserDTO outUserDTO = new OutUserDTO();

        outUserDTO.setId( user.getId() );
        outUserDTO.setUsername( user.getUsername() );
        outUserDTO.setEmail( user.getEmail() );
        outUserDTO.setPassword( user.getPassword() );
        outUserDTO.setFirstName( user.getFirstName() );
        outUserDTO.setLastName( user.getLastName() );

        return outUserDTO;
    }

    @Override
    public User toUser(InUserDTO inUserDTO) {
        if ( inUserDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( inUserDTO.getEmail() );
        user.setUsername( inUserDTO.getUsername() );
        user.setLastName( inUserDTO.getLastName() );
        user.setFirstName( inUserDTO.getFirstName() );
        user.setPassword( inUserDTO.getPassword() );

        return user;
    }
}
