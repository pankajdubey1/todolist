package com.tasktodo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tasktodo.model.Task;
import com.tasktodo.model.TaskResponseDto;
import com.tasktodo.service.impl.TaskToDoServiceImpl;

public class TaskToDoServiceTest {

	private TaskToDoService taskToDoservice;

	@BeforeEach
	public void setUp() {
		taskToDoservice = new TaskToDoServiceImpl();
	}

	@Test
	public void testAddTaskBoundaryCases() {
		assertEquals(null, taskToDoservice.addTask(null));
		assertEquals(null, taskToDoservice.addTask(""));
	}

	@Test
	public void testAddTask() {
		TaskResponseDto responseDto = taskToDoservice.addTask("Task1");
		String taskId = responseDto.getTaskId();
		Task expected = new Task(taskId, "Task1", false);
		Task actual = taskToDoservice.getTasks().get(taskId);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetTasks() {
		TaskResponseDto responseDto1 = taskToDoservice.addTask("description1");
		TaskResponseDto responseDto2 = taskToDoservice.addTask("description2");
		taskToDoservice.addTask("");

		String taskId1 = responseDto1.getTaskId();
		String taskId2 = responseDto2.getTaskId();
		Task expected1 = new Task(taskId1, "description1", false);
		Task actual1 = taskToDoservice.getTasks().get(taskId1);
		Task expected2 = new Task(taskId2, "description2", false);
		Task actual2 = taskToDoservice.getTasks().get(taskId2);

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(2, taskToDoservice.getTasks().size());
	}

	@Test
	public void testCompleteTaskBoundaryCases() {
		assertEquals(null, taskToDoservice.completeTask(""));
		assertEquals(null, taskToDoservice.completeTask(null));
	}

	@Test
	public void testCompleteTask() {
		TaskResponseDto responseDto = taskToDoservice.addTask("description");
		String taskId = responseDto.getTaskId();
		taskToDoservice.completeTask(taskId);
		Task expected = new Task(taskId, "description", true);
		Task actual = taskToDoservice.getTasks().get(taskId);
		assertEquals(expected, actual);
	}

	@Test
	public void updateTaskBoundaryCases() {
		assertEquals(null, taskToDoservice.updateTask("id", null));
		assertEquals(null, taskToDoservice.updateTask("id", ""));
		assertEquals(null, taskToDoservice.updateTask("", "desc"));
		assertEquals(null, taskToDoservice.updateTask(null, "desc"));
	}

	@Test
	public void testUpdateTask() {
		TaskResponseDto responseDto = taskToDoservice.addTask("descriptionOld");
		String taskId = responseDto.getTaskId();
		taskToDoservice.updateTask(taskId, "descriptionNew");
		assertEquals("descriptionNew", taskToDoservice.getTasks().get(taskId).getDesc());
	}

	@Test
	public void testDeleteCompletedTasks() {
		TaskResponseDto responseDto = taskToDoservice.addTask("description");
		String taskId = responseDto.getTaskId();
		taskToDoservice.completeTask(taskId);
		taskToDoservice.deleteCompletedTask();
		assertEquals(null, taskToDoservice.getTasks().get(taskId));
	}
}
