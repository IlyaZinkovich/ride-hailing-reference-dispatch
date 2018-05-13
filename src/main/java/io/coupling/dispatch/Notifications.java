package io.coupling.dispatch;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class Notifications extends AbstractLoggingActor {

  @Override
  public Receive createReceive() {
    return ReceiveBuilder.create()
        .match(MakeOffer.class, makeOffer -> {
          final long driverId = makeOffer.driverId;
          final long bookingId = makeOffer.bookingId;
          log().info("Made offer to driver {} for booking {}", driverId, bookingId);
        })
        .build();
  }

  public static Props props() {
    return Props.create(Notifications.class, Notifications::new);
  }
}
