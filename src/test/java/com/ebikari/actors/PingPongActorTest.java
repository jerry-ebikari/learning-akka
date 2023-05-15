package com.ebikari.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.Test;
import scala.concurrent.Future;

import java.util.concurrent.*;

import static akka.pattern.Patterns.ask;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static scala.compat.java8.FutureConverters.toJava;

class PingPongActorTest {

    ActorSystem actorSystem = ActorSystem.create();
    ActorRef actorRef = actorSystem.actorOf(Props.create(PingPongActor.class));

    @Test
    public void shouldReplyToPingWithPong() throws Exception {
        Future sFuture = ask(actorRef, "Ping", 1000);
        final CompletionStage<String> cs = toJava(sFuture);
        final CompletableFuture<String> jFuture = (CompletableFuture<String>) cs;
        assertEquals("Pong", jFuture.get(1000, TimeUnit.MILLISECONDS));
    }

    @Test
    public void shouldReplyToUnknownMessageWithFailure() throws Exception {
        Future sFuture = ask(actorRef, "unknown", 1000);
        final CompletionStage<String> cs = toJava(sFuture);
        final CompletableFuture<String> jFuture = (CompletableFuture<String>) cs;
        assertThrows(ExecutionException.class, () -> jFuture.get(1000, TimeUnit.MILLISECONDS));
    }

}