package com.example.taskflow.Controllers;

import com.example.taskflow.Entities.DTOs.Response.ResponseDTO;
import com.example.taskflow.Entities.DTOs.Token.TokenDTO;
import com.example.taskflow.Services.TokenService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/deletion")
    public ResponseEntity<TokenDTO> createDeletionToken(@RequestBody Map<String, String> requestBody) {
        try{
            UUID userId = UUID.fromString(requestBody.get("userId"));
            Integer taskId = Integer.parseInt(requestBody.get("taskId"));
            TokenDTO tokenDTO = tokenService.createDeletionToken(userId, taskId);
            return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }

    @PostMapping("/change")
    public ResponseEntity<ResponseDTO<TokenDTO>> createChangeToken(@RequestBody Map<String, String> requestBody) {
        try{
            UUID userId = UUID.fromString(requestBody.get("userId"));
            Integer taskId = Integer.parseInt(requestBody.get("taskId"));
            TokenDTO tokenDTO = tokenService.createChangeToken(userId, taskId);
            ResponseDTO<TokenDTO> response = new ResponseDTO<>(tokenDTO, "saved token");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<TokenDTO>>> allTokens(){
        try {
            List<TokenDTO> allTokens = tokenService.getAllTokens();
            ResponseDTO<List<TokenDTO>> response = new ResponseDTO<>(allTokens, "All tokens");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            throw ex;
        }
    }
}
