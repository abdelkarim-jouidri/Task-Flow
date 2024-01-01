package com.example.taskflow.Services;

import com.example.taskflow.Entities.DTOs.User.createUserDTO;
import com.example.taskflow.Entities.DTOs.User.viewUserDTO;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    viewUserDTO storeUser(createUserDTO user);
}
