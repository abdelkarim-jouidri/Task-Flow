package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.DTOs.Token.TokenDTO;
import com.example.taskflow.Entities.Enums.TokenType;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Entities.Models.Token;
import com.example.taskflow.Entities.Models.User;
import com.example.taskflow.Exceptions.NoTokenCreditException;
import com.example.taskflow.Mappings.TokenMapper;
import com.example.taskflow.Repositories.TaskRepository;
import com.example.taskflow.Repositories.TokenRepository;
import com.example.taskflow.Repositories.UserRepository;
import com.example.taskflow.Services.TokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TaskRepository taskRepository;
    private final TokenMapper tokenMapper = TokenMapper.INSTANCE;

    @Override
    public TokenDTO createChangeToken(UUID userId, Integer taskId) {
        User user = userRepository.
                findById(userId).
                orElseThrow(()->new EntityNotFoundException("user with userId="+userId+" not dound"));
        Task task = taskRepository.
                findById(taskId).
                orElseThrow(()->new EntityNotFoundException("task with id = "+taskId+" not found"));
        if(!user.getLastChangeTokenDate().toLocalDate().equals(LocalDate.now())){
            user.setDailyChangeTokens(2);
            user.setLastChangeTokenDate(LocalDateTime.now());
            userRepository.save(user);
        }
        if(user.getDailyChangeTokens()>0){
            Token builtToken = Token.builder().
                    createdAt(LocalDateTime.now()).
                    type(TokenType.CHANGE).
                    isConsumed(false).
                    user(user)
                    .task(task)
                    .build();
            user.setDailyChangeTokens(user.getDailyChangeTokens()-1);
            userRepository.save(user);
            tokenRepository.save(builtToken);
            return TokenMapper.INSTANCE.tokenToTokenDTO(builtToken);

        }else {
            throw new NoTokenCreditException("Daily change token credit is not sufficient");
        }

    }

    @Override
    public TokenDTO createDeletionToken(UUID userId, Integer taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with userId=" + userId + " not found"));
        Task task = taskRepository.
                findById(taskId).
                orElseThrow(()->new EntityNotFoundException("task with id = "+taskId+" not found"));
        if (!user.getLastChangeTokenDate().toLocalDate().getMonth().equals(LocalDate.now().getMonth())) {
            user.setMonthlyDeletionTokens(1);
            user.setLastDeletionTokenDate(LocalDateTime.now());
            userRepository.save(user);
            }

        if (user.getMonthlyDeletionTokens() > 0) {
            Token builtToken = Token.builder()
                    .createdAt(LocalDateTime.now())
                    .type(TokenType.DELETE)
                    .isConsumed(false)
                    .user(user)
                    .task(task)
                    .build();

            user.setMonthlyDeletionTokens(user.getMonthlyDeletionTokens() - 1);
            userRepository.save(user);
            tokenRepository.save(builtToken);
            return TokenMapper.INSTANCE.tokenToTokenDTO(builtToken);
        } else {
            throw new NoTokenCreditException("Monthly deletion token credit is not sufficient");
        }
    }

    @Override
    public List<TokenDTO> getAllTokens() {
        return tokenRepository.findAll().stream().map(tokenMapper::tokenToTokenDTO).collect(Collectors.toList());
    }



}
