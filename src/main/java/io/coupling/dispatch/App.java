package io.coupling.dispatch;

import static akka.actor.ActorRef.noSender;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class App {

  public static void main(final String[] args) {
    final ActorSystem system = ActorSystem.create("dispatch");
    final HashMap<Long, Set<Long>> userBlockedDrivers = new HashMap<>();
    final HashMap<Long, Set<Long>> driverBlockedUsers = new HashMap<>();
    final Map<Long, Booking> bookings = new HashMap<>();
    final double lat = 53.4D;
    final double lng = 72.3D;
    final Location location = new Location(lat, lng);
    final Map<Location, Set<Long>> driversInLocation = new HashMap<>();
    driversInLocation.put(location, Stream.of(1L, 2L, 3L).collect(toSet()));
    final DispatchActorsProperties dispatchActorsProperties =
        new DispatchActorsProperties(driverBlockedUsers, bookings, userBlockedDrivers,
            driversInLocation);
    final ActorRef dispatcherActor =
        system.actorOf(dispatchActorsProperties.dispatcher(), "dispatcher");
    final long userId = 1L;
    range(1, 101).forEach(bookingId ->
        dispatcherActor.tell(new Dispatch(userId, bookingId, location), noSender()));
  }
}
