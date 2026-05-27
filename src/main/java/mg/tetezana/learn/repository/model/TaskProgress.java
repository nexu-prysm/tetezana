package mg.tetezana.learn.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskProgress {
  @Id
  @UuidGenerator
  @ColumnDefault("gen_random_uuid()::varchar")
  private String id;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private AppUser user;

  @Enumerated(EnumType.STRING)
  private Status status;

  private String submissionData;

  public enum Status {
    STARTED,
    SUBMITTED,
    COMPLETED
  }
}
