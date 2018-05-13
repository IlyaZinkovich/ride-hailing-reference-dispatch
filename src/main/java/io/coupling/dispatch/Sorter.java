package io.coupling.dispatch;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Sorter extends AbstractActor {

  @Override
  public Receive createReceive() {
    return ReceiveBuilder.create()
        .match(SortDrivers.class, sortDrivers -> {
          final long bookingId = sortDrivers.bookingId;
          final Set<Long> driverIds = sortDrivers.driverIds;
          final TreeSet<Long> sortedDriverIdsSet = new TreeSet<>(
              (o1, o2) -> new Random().nextInt(3) - 1);
          sortedDriverIdsSet.addAll(driverIds);
          final List<Long> sortedDriverIds = new ArrayList<>(sortedDriverIdsSet);
          getSender().tell(new SortedDrivers(bookingId, sortedDriverIds), getSelf());
        })
        .build();
  }

  public static Props props() {
    return Props.create(Sorter.class, Sorter::new);
  }
}
