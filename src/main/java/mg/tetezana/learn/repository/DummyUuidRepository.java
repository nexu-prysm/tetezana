package mg.tetezana.learn.repository;

import java.util.List;
import mg.tetezana.learn.PojaGenerated;
import mg.tetezana.learn.repository.model.DummyUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@PojaGenerated
@Repository
public interface DummyUuidRepository extends JpaRepository<DummyUuid, String> {
  @Override
  List<DummyUuid> findAllById(Iterable<String> ids);
}
