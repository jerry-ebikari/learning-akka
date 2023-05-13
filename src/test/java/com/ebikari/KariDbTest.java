package com.ebikari;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.ebikari.messages.SetRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KariDbTest {

    // create ActorSystem, where actors and their addresses reside
    ActorSystem actorSystem = ActorSystem.create();

    @Test
    public void itShouldPlaceKeyValueFromSetMessageIntoMap() {

        // create ActorRef
        TestActorRef<KariDb> testActorRef = TestActorRef.create(actorSystem, Props.create(KariDb.class));

        // place message in actor's mailbox
        testActorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());

        // get reference to underlying actor instance
        KariDb kariDb = testActorRef.underlyingActor();
        assertEquals(kariDb.map.get("key"), "value");
    }

}