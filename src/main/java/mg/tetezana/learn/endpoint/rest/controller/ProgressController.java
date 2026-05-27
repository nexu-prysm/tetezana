package mg.tetezana.learn.endpoint.rest.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mg.tetezana.learn.repository.model.TaskProgress;
import mg.tetezana.learn.service.ProgressService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class ProgressController {

  private final ProgressService progressService;

  @PostMapping("/tasks/{taskId}/start")
  public TaskProgress startTask(@PathVariable String taskId, Authentication authentication) {
    return progressService.startTask(taskId, authentication.getName());
  }

  @PostMapping("/tasks/{taskId}/submit")
  public TaskProgress submitTask(
      @PathVariable String taskId,
      @RequestBody SubmitRequest request,
      Authentication authentication) {
    return progressService.submitTask(
        taskId, authentication.getName(), request.getSubmissionData());
  }

  @PostMapping("/tasks/{taskId}/complete")
  public TaskProgress completeTask(@PathVariable String taskId, Authentication authentication) {
    return progressService.completeTask(taskId, authentication.getName());
  }

  @Data
  public static class SubmitRequest {
    private String submissionData;
  }
}
