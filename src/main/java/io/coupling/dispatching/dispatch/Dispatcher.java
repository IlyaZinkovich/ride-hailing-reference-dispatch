package io.coupling.dispatching.dispatch;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import io.coupling.dispatching.filter.FilterDrivers;
import io.coupling.dispatching.filter.FilteredDrivers;
import io.coupling.dispatching.location.Location;
import io.coupling.dispatching.notify.MakeOffer;
import io.coupling.dispatching.sort.SortDrivers;
import io.coupling.dispatching.sort.SortedDrivers;
import io.coupling.dispatching.supply.SuppliedDrivers;
import io.coupling.dispatching.supply.Supply;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dispatcher extends AbstractActor {

  private static final String SUPPLIER = "supplier";
  private static final String FILTER = "filter";
  private static final String SORTER = "sorter";
  private static final String NOTIFICATIONS = "notifications";
  private static final int FIXED_DELAY_BETWEEN_PUSH = 1000;

  private final DispatchActorsProperties dispatchActorsProperties;
  private final Map<Long, Booking> bookings;

  private Dispatcher(final DispatchActorsProperties dispatchActorsProperties,
      final Map<Long, Booking> bookings) {
    this.dispatchActorsProperties = dispatchActorsProperties;
    this.bookings = bookings;
  }

  @Override
  public Receive createReceive() {
    return ReceiveBuilder.create()
        .match(Dispatch.class, dispatch -> {
          final long bookingId = dispatch.bookingId;
          final long userId = dispatch.userId;
          bookings.put(bookingId, new Booking(bookingId, userId));
          final Location location = dispatch.location;
          final Supply msg = new Supply(bookingId, location);
          final ActorRef supplier = getContext().findChild(SUPPLIER)
              .orElseGet(this::createSupplier);
          supplier.tell(msg, getSelf());
        })
        .match(SuppliedDrivers.class, suppliedDrivers -> {
          final long bookingId = suppliedDrivers.bookingId;
          final Booking booking = bookings.get(bookingId);
          final long userId = booking.userId;
          final Set<Long> driverIds = suppliedDrivers.driverIds;
          final ActorRef filter = getContext().findChild(FILTER)
              .orElseGet(this::createFilter);
          filter.tell(new FilterDrivers(bookingId, userId, driverIds), getSelf());
        })
        .match(FilteredDrivers.class, filteredDrivers -> {
          final long bookingId = filteredDrivers.bookingId;
          final Set<Long> driverIds = filteredDrivers.driverIds;
          final SortDrivers sortDrivers = new SortDrivers(bookingId, driverIds);
          final ActorRef sorter = getContext().findChild(SORTER).orElseGet(this::createSorter);
          sorter.tell(sortDrivers, getSelf());
        })
        .match(SortedDrivers.class, sortedDrivers -> {
          final long bookingId = sortedDrivers.bookingId;
          final List<Long> sortedDriverIds = sortedDrivers.sortedDriverIds;
          sortedDriverIds.stream().findFirst().ifPresent(driverId -> {
            final ActorRef notifications = getContext().findChild(NOTIFICATIONS)
                .orElseGet(this::createNotifications);
            notifications.tell(new MakeOffer(bookingId, driverId), getSelf());
            sortedDriverIds.remove(driverId);
            final ActorSystem system = getContext().getSystem();
            system.scheduler()
                .scheduleOnce(Duration.ofMillis(FIXED_DELAY_BETWEEN_PUSH), getSelf(),
                    new SortedDrivers(bookingId, sortedDriverIds), system.dispatcher(), getSelf());
          });
        })
        .build();
  }

  private ActorRef createFilter() {
    return getContext().actorOf(dispatchActorsProperties.filter(), FILTER);
  }

  private ActorRef createSupplier() {
    return getContext().actorOf(dispatchActorsProperties.supplier(), SUPPLIER);
  }

  private ActorRef createSorter() {
    return getContext().actorOf(dispatchActorsProperties.sorter(), SORTER);
  }

  private ActorRef createNotifications() {
    return getContext().actorOf(dispatchActorsProperties.notifications(), NOTIFICATIONS);
  }

  public static Props props(final DispatchActorsProperties dispatchActorsProperties,
      final Map<Long, Booking> bookings) {
    return Props.create(Dispatcher.class, () -> new Dispatcher(dispatchActorsProperties, bookings));
  }
}
