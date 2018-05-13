package io.coupling.dispatch;

import java.util.Set;

public class FilteredDrivers {

  final long bookingId;
  final Set<Long> driverIds;

  FilteredDrivers(final long bookingId, final Set<Long> driverIds) {
    this.bookingId = bookingId;
    this.driverIds = driverIds;
  }
}
