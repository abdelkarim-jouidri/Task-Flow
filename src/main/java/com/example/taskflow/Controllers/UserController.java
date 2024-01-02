package com.example.taskflow.Controllers;

import com.example.taskflow.Entities.DTOs.Response.ResponseDTO;
import com.example.taskflow.Entities.DTOs.User.createUserDTO;
import com.example.taskflow.Entities.DTOs.User.viewUserDTO;
import com.example.taskflow.Entities.Models.User;
import com.example.taskflow.Repositories.UserRepository;
import com.example.taskflow.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/")
@ControllerAdvice
@RequiredArgsConstructor
public class UserController {
    @Qualifier("userServiceImpl")
    private final UserService userService;
    private final UserRepository userRepository;
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO<viewUserDTO>> AddUser(@Valid @RequestBody createUserDTO user){
        try {
            viewUserDTO storedUser = userService.storeUser(user);
            ResponseDTO<viewUserDTO> response = new ResponseDTO<>(storedUser, "data");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }

    @GetMapping("/isAdmin")
    public boolean isUserAdmin(UUID userId){
        User userWithRoleAdmin = userRepository.findUserWithRoleAdmin(userId);
        if (userWithRoleAdmin!=null) return true;
        else return false;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<User>>> getAll(){
        List<User> all = userRepository.findAll();
        ResponseDTO response = new ResponseDTO(all, "data");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
