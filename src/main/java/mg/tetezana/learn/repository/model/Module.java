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
public class Module {
  @Id
  @UuidGenerator
  @ColumnDefault("gen_random_uuid()::varchar")
  private String id;

  @ManyToOne
  @JoinColumn(name = "internship_track_id")
  private InternshipTrack internshipTrack;

  private String title;

  private String description;

  private Integer orderIndex;
}
