package io.coupling.dispatch;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import java.util.Map;
import java.util.Set;

public class Supplier extends AbstractActor {

  private final Map<Location, Set<Long>> driversInLocation;

  private Supplier(final Map<Location, Set<Long>> driversInLocation) {
    this.driversInLocation = driversInLocation;
  }

  @Override
  public Receive createReceive() {
    return ReceiveBuilder.create()
        .match(Supply.class, supply -> {
          final long bookingId = supply.bookingId;
          final Location location = supply.location;
          final Set<Long> driverIds = driversInLocation.get(location);
          getSender().tell(new SuppliedDrivers(bookingId, driverIds), getSelf());
        })
        .build();
  }

  public static Props props(final Map<Location, Set<Long>> driversInLocation) {
    return Props.create(Supplier.class, () -> new Supplier(driversInLocation));
  }
}
