package com.example.taskflow.Entities.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseDTO<T> {
    private T data;
    private String msg;

    public ResponseDTO(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }
}
