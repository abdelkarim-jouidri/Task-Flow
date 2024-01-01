package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.DTOs.User.createUserDTO;
import com.example.taskflow.Entities.DTOs.User.viewUserDTO;
import com.example.taskflow.Entities.Models.Role;
import com.example.taskflow.Entities.Models.User;
import com.example.taskflow.Mappings.UserMapper;
import com.example.taskflow.Repositories.RoleRepository;
import com.example.taskflow.Repositories.UserRepository;
import com.example.taskflow.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("userServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public viewUserDTO storeUser(createUserDTO user) {
        List<String> roleAuthorities = user.
                getAuthorities().
                stream().
                map(authority -> authority.getAuthority()).
                collect(Collectors.toList());
        Set<Role> roles = new HashSet<>();
        for(String authority : roleAuthorities){
            Optional<Role> roleByAuthority = roleRepository.findByAuthority(authority);
            if(roleByAuthority.isEmpty()){
                throw new NoSuchElementException("No such role for authority : "+authority);
            }
            roles.add(roleByAuthority.get());
        }
        User userDTOtoUser = UserMapper.INSTANCE.createUserDTOtoUser(user);
        userDTOtoUser.setId(UUID.randomUUID());
        userDTOtoUser.setAuthorities(roles);
        User storedUser = userRepository.save(userDTOtoUser);
        return UserMapper.INSTANCE.userToViewUserDTO(storedUser);
    }
}
