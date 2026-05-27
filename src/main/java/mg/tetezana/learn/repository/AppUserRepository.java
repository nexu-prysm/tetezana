package mg.tetezana.learn.repository;

import java.util.Optional;
import mg.tetezana.learn.repository.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
  Optional<AppUser> findByEmail(String email);
}
