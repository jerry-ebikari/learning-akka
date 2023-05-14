package com.ebikari.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;

public class PingPongActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("Ping", message -> sender().tell("Pong", ActorRef.noSender()))
                .matchAny(o -> sender().tell(new Status.Failure(new Exception("Unknown message")), self()))
                .build();
    }
}
