package mg.tetezana.learn.endpoint.rest.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mg.tetezana.learn.repository.TaskRepository;
import mg.tetezana.learn.repository.model.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskRepository taskRepository;

  @GetMapping
  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  @GetMapping("/{id}")
  public Task getTaskById(@PathVariable String id) {
    return taskRepository.findById(id).orElseThrow();
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Task createTask(@RequestBody Task task) {
    return taskRepository.save(task);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Task updateTask(@PathVariable String id, @RequestBody Task taskDetails) {
    Task task = taskRepository.findById(id).orElseThrow();
    task.setTitle(taskDetails.getTitle());
    task.setDescription(taskDetails.getDescription());
    task.setOrderIndex(taskDetails.getOrderIndex());
    task.setContainerTrack(taskDetails.getContainerTrack());
    task.setVmTrack(taskDetails.getVmTrack());
    task.setModule(taskDetails.getModule());
    return taskRepository.save(task);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteTask(@PathVariable String id) {
    taskRepository.deleteById(id);
  }
}
