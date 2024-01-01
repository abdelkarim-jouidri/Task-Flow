package com.example.taskflow.Controllers;

import com.example.taskflow.Entities.DTOs.Response.ResponseDTO;
import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Services.Impl.TaskServiceImpl;
import com.example.taskflow.Services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tasks")
@RestControllerAdvice
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/save")
    public ResponseEntity<ResponseDTO<TaskDTO>> AddTask( @Valid @RequestBody TaskDTO taskDTO){
        try{
            TaskDTO savedTask = taskService.addTask(taskDTO);
            ResponseDTO<TaskDTO> response = new ResponseDTO<TaskDTO>(savedTask, "saved");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }

    @PostMapping("/status/{taskId}/edit")
    public ResponseEntity<ResponseDTO<TaskDTO>> EditStatus(@PathVariable Integer taskId){
        try{
            TaskDTO updatedTask = taskService.markTaskAsDone(taskId);
            ResponseDTO<TaskDTO> response = new ResponseDTO<>(updatedTask, "updated task");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }
}
