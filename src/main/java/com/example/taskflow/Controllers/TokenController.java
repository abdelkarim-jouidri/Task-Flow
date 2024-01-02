package com.example.taskflow.Controllers;

import com.example.taskflow.Entities.DTOs.Response.ResponseDTO;
import com.example.taskflow.Entities.DTOs.Token.TokenDTO;
import com.example.taskflow.Services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/deletion")
    public ResponseEntity<TokenDTO> createDeletionToken(@RequestBody Map<String, UUID> requestBody) {
        try{
            UUID userId = requestBody.get("userId");
            TokenDTO tokenDTO = tokenService.createDeletionToken(userId);
            return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }

    @PostMapping("/change")
    public ResponseEntity<ResponseDTO<TokenDTO>> createChangeToken(@RequestBody Map<String, UUID> requestBody) {
        try{
            UUID userId = requestBody.get("userId");
            TokenDTO tokenDTO = tokenService.createChangeToken(userId);
            ResponseDTO<TokenDTO> response = new ResponseDTO<>(tokenDTO, "saved token");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }
}
