package edu.northwestu.intc3283.datasourcestarter.tasks.controller;

import edu.northwestu.intc3283.datasourcestarter.tasks.entity.Task;
import edu.northwestu.intc3283.datasourcestarter.tasks.entity.TaskRequest;
import edu.northwestu.intc3283.datasourcestarter.tasks.repository.TasksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TasksApicontroller {

    private final TasksRepository tasksRepository;

    public TasksApicontroller(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable("id") Long id) {
        Optional<Task> taskOptional = this.tasksRepository.findById(id);

        if (taskOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        return taskOptional.get();
    }

    @GetMapping("")
    public List<Task> getTaskList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(
                page,
                pageSize
        );

        final Page<Task> results = this.tasksRepository.findAll(pageable);
        return results.getContent();
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") Long id, @Validated @RequestBody TaskRequest taskRequest) {
        Task task = this.tasksRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        this.tasksRepository.save(task);
        return task;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        if (this.tasksRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Task already deleted");
        }

        this.tasksRepository.deleteById(id);
    }

    @PostMapping("")
    public Task createTask(@Validated @RequestBody Task task) {
        task.setStatus("PENDING");
        return this.tasksRepository.save(task);
    }
}
