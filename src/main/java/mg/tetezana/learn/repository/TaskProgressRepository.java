package mg.tetezana.learn.repository;

import java.util.Optional;
import mg.tetezana.learn.repository.model.TaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskProgressRepository extends JpaRepository<TaskProgress, String> {
  Optional<TaskProgress> findByTaskIdAndUserId(String taskId, String userId);
}
