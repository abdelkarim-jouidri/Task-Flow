package com.example.taskflow.Mappings;

import com.example.taskflow.Entities.DTOs.Tag.TagDTO;
import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Models.Tag;
import com.example.taskflow.Entities.Models.Task;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface Mapper {
    Mapper INSTANCE  = Mappers.getMapper(Mapper.class);

    TaskDTO taskToTaskDTO(Task task);
    Task taskDTOtoTask(TaskDTO taskDTO);
    TagDTO tagToTagDTO(Tag tag);
}
