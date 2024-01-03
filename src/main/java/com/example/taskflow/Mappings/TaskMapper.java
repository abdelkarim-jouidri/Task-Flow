package com.example.taskflow.Mappings;

import com.example.taskflow.Entities.DTOs.Tag.TagDTO;
import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Models.Tag;
import com.example.taskflow.Entities.Models.Task;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@org.mapstruct.Mapper
public interface TaskMapper {
    TaskMapper INSTANCE  = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "assignedBy", source = "assignedBy")
    @Mapping(target = "assignedTo", source = "assignedTo")
    TaskDTO taskToTaskDTO(Task task);
    @Mapping(target = "assignedBy", source = "assignedBy")
    @Mapping(target = "assignedTo", source = "assignedTo")
    Task taskDTOtoTask(TaskDTO taskDTO);
    TagDTO tagToTagDTO(Tag tag);
    Set<Tag> mapTagDTOSetToTagSet(Set<TagDTO> tagDTOSet);

}
