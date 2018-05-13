package io.coupling.dispatching.supply;

import java.util.Set;

public class SuppliedDrivers {

  public final long bookingId;
  public final Set<Long> driverIds;

  SuppliedDrivers(final long bookingId, final Set<Long> driverIds) {
    this.bookingId = bookingId;
    this.driverIds = driverIds;
  }
}
