package com.example.tasknotes.tasks;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TasksService {
    private TasksRepository tasksRepository;
    private ModelMapper modelMapper;

    public TasksService(TasksRepository tasksRepository, ModelMapper modelMapper){
        this.tasksRepository = tasksRepository;
        this.modelMapper = modelMapper;
    }

    List<TaskDto> getAllTheTasks() {
        var tasks = tasksRepository.findAll();
        return tasks
                .stream()
                .map(task -> modelMapper.map(task, TaskDto.class)).toList();

    }

    TaskDto createNewTask(TaskDto task) {

        if(task.getDueDate() != null && task.getDueDate().before(new Date())){
            throw new TaskAlreadyExistsException("Task due date is in the past");
        }

        if((task.getName() != null && task.getName().length() <= 1) || task.getDueDate() == null){
            throw new TaskInvalidException("Task name or due date is null");
        }
        var taskEntity = modelMapper.map(task,TaskEntity.class);
        var savedTask = tasksRepository.save(taskEntity);
        return modelMapper.map(savedTask, TaskDto.class);
    }
    TaskDto getTaskById(Long taskId) {
        TaskEntity task  = tasksRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        TaskDto taskDto = modelMapper.map(task,TaskDto.class);

        return taskDto;
    }

    static class TaskNotFoundException extends IllegalArgumentException{
        public TaskNotFoundException(String message) {
            super(message);
        }
    }
    static class TaskAlreadyExistsException extends IllegalArgumentException{
        public TaskAlreadyExistsException(String message) {
            super(message);
        }
    }
    static class TaskInvalidException extends IllegalArgumentException{
        public TaskInvalidException(String message) {
            super(message);
        }
    }
}