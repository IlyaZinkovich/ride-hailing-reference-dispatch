package io.coupling.dispatching.filter;

import java.util.Set;

public class FilteredDrivers {

  public final long bookingId;
  public final Set<Long> driverIds;

  FilteredDrivers(final long bookingId, final Set<Long> driverIds) {
    this.bookingId = bookingId;
    this.driverIds = driverIds;
  }
}
