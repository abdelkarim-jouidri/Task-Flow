package com.example.taskflow;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Mappings.TaskMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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
				dueDate(LocalDate.now().plusDays(2)).
				createdAt(LocalDate.now()).
				build();

		Task mappedTask = TaskMapper.INSTANCE.taskDTOtoTask(taskDTO);
		assertThat(mappedTask).isNotNull();
		assertThat(mappedTask.getDescription()).isNotEmpty();
		assertThat(mappedTask.getTaskStatus()).isEqualTo(TaskStatus.TO_DO);

	}

}
