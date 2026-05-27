package mg.tetezana.learn.repository.model;

import jakarta.persistence.Entity;
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
public class Task {
  @Id
  @UuidGenerator
  @ColumnDefault("gen_random_uuid()::varchar")
  private String id;

  @ManyToOne
  @JoinColumn(name = "module_id")
  private Module module;

  private String title;

  private String description;

  private Boolean containerTrack;

  private Boolean vmTrack;

  private Integer orderIndex;
}
