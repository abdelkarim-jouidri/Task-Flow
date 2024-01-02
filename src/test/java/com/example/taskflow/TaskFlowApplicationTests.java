package com.example.taskflow;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Mappings.TaskMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TaskFlowApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void shouldMapCorrectly(){
		TaskDTO taskDTO = TaskDTO.builder().
				taskStatus(TaskStatus.TO_DO).
				description("lorem ipsum").
				dueDate(LocalDateTime.now().plusDays(2)).
				build();

		Task mappedTask = TaskMapper.INSTANCE.taskDTOtoTask(taskDTO);
		assertThat(mappedTask).isNotNull();
		assertThat(mappedTask.getDescription()).isNotEmpty();
		assertThat(mappedTask.getTaskStatus()).isEqualTo(TaskStatus.TO_DO);

	}

}
