package io.coupling.dispatching.filter;

import java.util.Set;

public class FilterDrivers {

  final long bookingId;
  final long userId;
  final Set<Long> driverIds;

  public FilterDrivers(final long bookingId, final long userId, final Set<Long> driverIds) {
    this.bookingId = bookingId;
    this.userId = userId;
    this.driverIds = driverIds;
  }
}
