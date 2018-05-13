package io.coupling.dispatching.sort;

import java.util.Set;

public class SortDrivers {

  final long bookingId;
  final Set<Long> driverIds;

  public SortDrivers(final long bookingId, final Set<Long> driverIds) {
    this.bookingId = bookingId;
    this.driverIds = driverIds;
  }
}
