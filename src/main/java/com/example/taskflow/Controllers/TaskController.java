package com.example.taskflow.Controllers;

import com.example.taskflow.Entities.DTOs.Request.changeTaskDTO;
import com.example.taskflow.Entities.DTOs.Request.deleteTaskDTO;
import com.example.taskflow.Entities.DTOs.Response.ResponseDTO;
import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Repositories.TaskRepository;
import com.example.taskflow.Services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/tasks")
@RestControllerAdvice
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO<TaskDTO>> AddTask( @Valid @RequestBody TaskDTO taskDTO){
        try{
            TaskDTO savedTask = taskService.addTask(taskDTO);
            ResponseDTO<TaskDTO> response = new ResponseDTO<TaskDTO>(savedTask, "saved");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception ex){
            ex.printStackTrace();
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
            ex.printStackTrace();
            throw ex;
        }
    }

    @PostMapping("/change")
    public ResponseEntity<ResponseDTO<?>> changeTask(@RequestBody @Valid changeTaskDTO changeTaskDTO){
        try{
            UUID managerId = changeTaskDTO.getManagerId();
            Integer tokenId = changeTaskDTO.getTokenId();
            taskService.changeTask(managerId, tokenId);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(null, "successfully changed");
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }

    @PostMapping("/request/delete")
    public ResponseEntity<ResponseDTO<?>> consumeDeleteRequest(@RequestBody @Valid changeTaskDTO changeTaskDTO){
        try{
            UUID managerId = changeTaskDTO.getManagerId();
            Integer tokenId = changeTaskDTO.getTokenId();
            taskService.deleteTaskForTokenDeletionRequest(managerId, tokenId);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(null, "request successfully consumed");
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }catch (Exception ex){
            throw ex;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<TaskDTO>>> allTasks(){
        try{
            List<TaskDTO> all = taskService.getAllTasks();
            ResponseDTO<List<TaskDTO>> response = new ResponseDTO<>(all, "All tasks");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            throw ex;
        }
    }

    @GetMapping("/test")
    public Task nonAssignedTask(){
        Optional<Task> aNonAssignedTask = taskRepository.findReplaceableNonAssignedTaskWithDefaultMaxResults();
        if(aNonAssignedTask.isPresent()) return aNonAssignedTask.get();
        return null;
    }

    @GetMapping("/overdue_tasks")
    public List<Task> overdueTasks(){
       return taskRepository.findOverdueTasks();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO<?>> deleteTask(@Valid @RequestBody deleteTaskDTO deleteTaskDTO){
        try {
            taskService.deleteTask(deleteTaskDTO.getUserId(), deleteTaskDTO.getTaskId());
            ResponseDTO<?> responseDTO = new ResponseDTO<>(null, "Deleted Successfully");
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception ex){
            throw ex;
        }

    }
}
