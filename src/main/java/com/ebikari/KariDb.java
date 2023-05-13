package com.ebikari;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.ebikari.messages.SetRequest;

import java.util.HashMap;
import java.util.Map;

public class KariDb extends AbstractActor {

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    protected final Map<String, Object> map = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SetRequest.class, message -> {
                    log.info("Received set request - key: {}, value: {}", message.getKey(), message.getValue());
                    map.put(message.getKey(), message.getValue());
                })
                .matchAny(o -> log.info("Received unknown message {}", o))
                .build();
    }
}
