package io.coupling.dispatching.dispatch;

import akka.actor.Props;
import io.coupling.dispatching.filter.Filter;
import io.coupling.dispatching.notify.Notifications;
import io.coupling.dispatching.sort.Sorter;
import io.coupling.dispatching.supply.Supplier;
import java.util.Map;
import java.util.Set;

public class DispatchActorsProperties {

  private final Map<Long, Set<Long>> userBlockedDrivers;
  private final Map<Long, Set<Long>> driverBlockedUsers;
  private final Map<Long, Booking> bookings;
  private final Map<String, Set<Long>> driversInLocation;

  public DispatchActorsProperties(final Map<Long, Set<Long>> driverBlockedUsers,
      final Map<Long, Booking> bookings,
      final Map<Long, Set<Long>> userBlockedDrivers,
      final Map<String, Set<Long>> driversInLocation) {
    this.bookings = bookings;
    this.userBlockedDrivers = userBlockedDrivers;
    this.driverBlockedUsers = driverBlockedUsers;
    this.driversInLocation = driversInLocation;
  }

  public Props filter() {
    return Filter.props(userBlockedDrivers, driverBlockedUsers);
  }

  public Props supplier() {
    return Supplier.props(driversInLocation);
  }

  public Props dispatcher() {
    final DispatchActorsProperties dispatchActorsProperties = this;
    return Dispatcher.props(dispatchActorsProperties, bookings);
  }

  public Props sorter() {
    return Sorter.props();
  }

  public Props notifications() {
    return Notifications.props();
  }
}
