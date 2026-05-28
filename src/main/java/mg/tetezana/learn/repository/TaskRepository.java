package mg.tetezana.learn.repository;

import java.util.List;
import mg.tetezana.learn.repository.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
  List<Task> findByModule_InternshipTrack_Id(String trackId);
}
