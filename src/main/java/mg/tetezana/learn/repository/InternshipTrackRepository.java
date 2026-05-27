package mg.tetezana.learn.repository;

import mg.tetezana.learn.repository.model.InternshipTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipTrackRepository extends JpaRepository<InternshipTrack, String> {}
