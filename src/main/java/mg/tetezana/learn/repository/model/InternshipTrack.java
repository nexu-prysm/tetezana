package mg.tetezana.learn.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class InternshipTrack {
  @Id
  @UuidGenerator
  @ColumnDefault("gen_random_uuid()::varchar")
  private String id;

  private String title;

  private String description;
}
