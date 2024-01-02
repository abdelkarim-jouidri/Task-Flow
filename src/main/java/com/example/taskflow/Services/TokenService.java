package com.example.taskflow.Services;

import com.example.taskflow.Entities.DTOs.Token.TokenDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface TokenService {
    TokenDTO createChangeToken(UUID userId);
    TokenDTO createDeletionToken(UUID userId);
}
