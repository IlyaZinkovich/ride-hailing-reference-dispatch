package io.coupling.dispatch;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import java.util.Map;
import java.util.Set;

public class Filter extends AbstractActor {

  private static final Set<Long> EMPTY_SET = emptySet();

  private final Map<Long, Set<Long>> userBlockedDrivers;
  private final Map<Long, Set<Long>> driverBlockedUsers;

  private Filter(final Map<Long, Set<Long>> userBlockedDrivers,
      final Map<Long, Set<Long>> driverBlockedUsers) {
    this.userBlockedDrivers = userBlockedDrivers;
    this.driverBlockedUsers = driverBlockedUsers;
  }

  @Override
  public Receive createReceive() {
    return ReceiveBuilder.create()
        .match(FilterDrivers.class, filterDrivers -> {
          final long bookingId = filterDrivers.bookingId;
          final Set<Long> driverIds = filterDrivers.driverIds;
          final long userId = filterDrivers.userId;
          final Set<Long> filteredDriverIds = driverIds.stream()
              .filter(driverId -> !userBlockedDrivers.getOrDefault(userId, EMPTY_SET)
                  .contains(driverId))
              .filter(driverId -> !driverBlockedUsers.getOrDefault(driverId, EMPTY_SET)
                  .contains(userId))
              .collect(toSet());
          final FilteredDrivers msg = new FilteredDrivers(bookingId, filteredDriverIds);
          getSender().tell(msg, getSelf());
        })
        .build();
  }

  public static Props props(final Map<Long, Set<Long>> userBlockedDrivers,
      final Map<Long, Set<Long>> driverBlockedUsers) {
    return Props.create(Filter.class, () -> new Filter(userBlockedDrivers, driverBlockedUsers));
  }
}
