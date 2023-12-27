package com.example.taskflow.Controllers;

import com.example.taskflow.Entities.DTOs.Response.ResponseDTO;
import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Mappings.Mapper;
import com.example.taskflow.Services.Impl.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tasks")
@RestControllerAdvice
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;


    @PostMapping("/save")
    public ResponseDTO<TaskDTO> AddTask( @Valid @RequestBody TaskDTO taskDTO){
        try{
            TaskDTO savedTask = taskService.addTask(taskDTO);
            ResponseDTO<TaskDTO> response = new ResponseDTO<TaskDTO>(savedTask, "saved");
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
        Task task = Mapper.INSTANCE.taskDTOtoTask(taskDTO);
        return new ResponseDTO<>(task, "data");
    }
}
