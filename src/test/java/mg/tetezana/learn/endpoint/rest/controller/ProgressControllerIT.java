package mg.tetezana.learn.endpoint.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mg.tetezana.learn.conf.FacadeIT;
import mg.tetezana.learn.repository.AppUserRepository;
import mg.tetezana.learn.repository.InternshipTrackRepository;
import mg.tetezana.learn.repository.ModuleRepository;
import mg.tetezana.learn.repository.TaskProgressRepository;
import mg.tetezana.learn.repository.TaskRepository;
import mg.tetezana.learn.repository.model.AppUser;
import mg.tetezana.learn.repository.model.InternshipTrack;
import mg.tetezana.learn.repository.model.Module;
import mg.tetezana.learn.repository.model.Task;
import mg.tetezana.learn.repository.model.TaskProgress;
import mg.tetezana.learn.service.security.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class ProgressControllerIT extends FacadeIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private AppUserRepository userRepository;

  @Autowired private InternshipTrackRepository trackRepository;

  @Autowired private ModuleRepository moduleRepository;

  @Autowired private TaskRepository taskRepository;

  @Autowired private TaskProgressRepository progressRepository;

  @Autowired private AuthService authService;

  @Test
  void can_start_and_submit_task() throws Exception {
    // Given
    AppUser student =
        userRepository.save(
            AppUser.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@test.com")
                .passwordHash("hashedPwd")
                .role(AppUser.Role.STUDENT)
                .build());

    InternshipTrack track =
        trackRepository.save(InternshipTrack.builder().title("SOC Tier 1").build());

    Module module =
        moduleRepository.save(
            Module.builder().title("Log Analysis").internshipTrack(track).orderIndex(1).build());

    Task task =
        taskRepository.save(
            Task.builder()
                .title("Analyze Kibana Logs")
                .module(module)
                .containerTrack(true)
                .vmTrack(false)
                .orderIndex(1)
                .build());

    String token = authService.generateToken(student);

    // When - Start Task
    mockMvc
        .perform(
            post("/progress/tasks/" + task.getId() + "/start")
                .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());

    // Then
    TaskProgress progress =
        progressRepository.findByTaskIdAndUserId(task.getId(), student.getId()).orElseThrow();
    assertEquals(TaskProgress.Status.STARTED, progress.getStatus());

    // When - Submit Task
    String submitJson = "{\"submissionData\":\"Found C2 beaconing on IP 10.0.0.5\"}";
    mockMvc
        .perform(
            post("/progress/tasks/" + task.getId() + "/submit")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(submitJson))
        .andExpect(status().isOk());

    // Then
    progress =
        progressRepository.findByTaskIdAndUserId(task.getId(), student.getId()).orElseThrow();
    assertEquals(TaskProgress.Status.SUBMITTED, progress.getStatus());
    assertEquals("Found C2 beaconing on IP 10.0.0.5", progress.getSubmissionData());
  }
}
