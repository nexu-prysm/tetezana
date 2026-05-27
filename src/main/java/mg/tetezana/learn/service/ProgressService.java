package mg.tetezana.learn.service;

import lombok.RequiredArgsConstructor;
import mg.tetezana.learn.repository.AppUserRepository;
import mg.tetezana.learn.repository.TaskProgressRepository;
import mg.tetezana.learn.repository.TaskRepository;
import mg.tetezana.learn.repository.model.AppUser;
import mg.tetezana.learn.repository.model.Task;
import mg.tetezana.learn.repository.model.TaskProgress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProgressService {

  private final TaskProgressRepository progressRepository;
  private final TaskRepository taskRepository;
  private final AppUserRepository userRepository;

  @Transactional
  public TaskProgress startTask(String taskId, String userEmail) {
    Task task =
        taskRepository
            .findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    AppUser user =
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    TaskProgress progress =
        progressRepository
            .findByTaskIdAndUserId(taskId, user.getId())
            .orElseGet(() -> TaskProgress.builder().task(task).user(user).build());

    progress.setStatus(TaskProgress.Status.STARTED);
    return progressRepository.save(progress);
  }

  @Transactional
  public TaskProgress submitTask(String taskId, String userEmail, String submissionData) {
    AppUser user =
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    TaskProgress progress =
        progressRepository
            .findByTaskIdAndUserId(taskId, user.getId())
            .orElseThrow(() -> new IllegalStateException("Task not started yet"));

    progress.setSubmissionData(submissionData);
    progress.setStatus(TaskProgress.Status.SUBMITTED);
    return progressRepository.save(progress);
  }

  @Transactional
  public TaskProgress completeTask(String taskId, String userEmail) {
    AppUser user =
        userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    TaskProgress progress =
        progressRepository
            .findByTaskIdAndUserId(taskId, user.getId())
            .orElseThrow(() -> new IllegalStateException("Task progress not found"));

    // Here we could verify the submission data logic if automated
    progress.setStatus(TaskProgress.Status.COMPLETED);
    return progressRepository.save(progress);
  }
}
