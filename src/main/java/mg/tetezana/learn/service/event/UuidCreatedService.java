package mg.tetezana.learn.service.event;

import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.tetezana.learn.PojaGenerated;
import mg.tetezana.learn.endpoint.event.model.UuidCreated;
import mg.tetezana.learn.repository.DummyUuidRepository;
import mg.tetezana.learn.repository.model.DummyUuid;
import org.springframework.stereotype.Service;

@PojaGenerated
@Service
@AllArgsConstructor
@Slf4j
public class UuidCreatedService implements Consumer<UuidCreated> {

  private final DummyUuidRepository dummyUuidRepository;

  @Override
  public void accept(UuidCreated uuidCreated) {
    var dummyUuid = new DummyUuid();
    dummyUuid.setId(uuidCreated.getUuid());
    dummyUuidRepository.save(dummyUuid);
  }
}
