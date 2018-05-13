package io.coupling.dispatch;

import java.util.Set;

public class SuppliedDrivers {

  final long bookingId;
  final Set<Long> driverIds;

  SuppliedDrivers(final long bookingId, final Set<Long> driverIds) {
    this.bookingId = bookingId;
    this.driverIds = driverIds;
  }
}
