package com.example.taskflow.Mappings;

import com.example.taskflow.Entities.DTOs.Tag.TagDTO;
import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Models.Tag;
import com.example.taskflow.Entities.Models.Task;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@org.mapstruct.Mapper
public interface TaskMapper {
    TaskMapper INSTANCE  = Mappers.getMapper(TaskMapper.class);

    TaskDTO taskToTaskDTO(Task task);
    Task taskDTOtoTask(TaskDTO taskDTO);
    TagDTO tagToTagDTO(Tag tag);
    Set<Tag> mapTagDTOSetToTagSet(Set<TagDTO> tagDTOSet);

}
