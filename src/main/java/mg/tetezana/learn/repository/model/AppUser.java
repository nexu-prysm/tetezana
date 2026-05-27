package mg.tetezana.learn.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "\"app_user\"")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUser {
  @Id
  @UuidGenerator
  @ColumnDefault("gen_random_uuid()::varchar")
  private String id;

  private String firstName;

  private String lastName;

  private String email;

  private String passwordHash;

  @Enumerated(EnumType.STRING)
  private Role role;

  public enum Role {
    STUDENT,
    COMPANY,
    ADMIN
  }
}
