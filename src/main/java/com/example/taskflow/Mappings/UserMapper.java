package com.example.taskflow.Mappings;

import com.example.taskflow.Entities.DTOs.User.createUserDTO;
import com.example.taskflow.Entities.DTOs.User.viewUserDTO;
import com.example.taskflow.Entities.Models.User;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface UserMapper {
    UserMapper INSTANCE  = Mappers.getMapper(UserMapper.class);

    viewUserDTO userToViewUserDTO(User user);
    User viewUserDTOtoUser(viewUserDTO viewUserDTO);

    User createUserDTOtoUser(createUserDTO createUserDTO);


}
