package mg.tetezana.learn.endpoint.event.consumer.model;

import mg.tetezana.learn.PojaGenerated;
import mg.tetezana.learn.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
