package io.coupling.dispatch;

import java.util.Set;

public class SortDrivers {

  final long bookingId;
  final Set<Long> driverIds;

  SortDrivers(final long bookingId, final Set<Long> driverIds) {
    this.bookingId = bookingId;
    this.driverIds = driverIds;
  }
}
