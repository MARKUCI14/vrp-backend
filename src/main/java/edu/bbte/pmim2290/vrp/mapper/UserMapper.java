package edu.bbte.pmim2290.vrp.mapper;

import edu.bbte.pmim2290.vrp.dto.InUserDTO;
import edu.bbte.pmim2290.vrp.dto.OutUserDTO;
import edu.bbte.pmim2290.vrp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    OutUserDTO toOutUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    User toUser(InUserDTO inUserDTO);
}
