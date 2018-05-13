package io.coupling.dispatching.supply;

import io.coupling.dispatching.location.Location;

public class Supply {

  final long bookingId;
  final Location location;

  public Supply(final long bookingId, final Location location) {
    this.bookingId = bookingId;
    this.location = location;
  }
}
