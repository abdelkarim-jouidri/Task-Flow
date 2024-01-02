package com.example.taskflow.Mappings;

import com.example.taskflow.Entities.DTOs.Token.TokenDTO;
import com.example.taskflow.Entities.Models.Token;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenMapper {
    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);
    TokenDTO tokenToTokenDTO(Token token);
    Token tokenDTOToToken(TokenDTO tokenDTO);
}
