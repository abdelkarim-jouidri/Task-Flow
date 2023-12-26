package com.example.taskflow.Controllers;

import com.example.taskflow.Entities.DTOs.Response.ResponseDTO;
import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Mappings.TaskMapper;
import com.example.taskflow.Services.Impl.TaskServiceImpl;
import com.example.taskflow.Services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tasks")
@RestControllerAdvice
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;


    @PostMapping("/save")
    public ResponseDTO<TaskDTO> AddTask( @RequestBody TaskDTO taskDTO){
        try{
            TaskDTO taskDTO1 = taskDTO;
            TaskDTO savedTask = taskService.addTask(taskDTO);
            ResponseDTO<TaskDTO> response = new ResponseDTO<TaskDTO>(taskDTO1, "saved");
            return response;
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @GetMapping("")
    public String Test(){
        return "HELLO, WORLD";
    }

    @PostMapping("/test")
    public ResponseDTO<Task> test(@RequestBody TaskDTO taskDTO){
        Task task = TaskMapper.INSTANCE.taskDTOtoTask(taskDTO);
        return new ResponseDTO<>(task, "data");
    }
}
