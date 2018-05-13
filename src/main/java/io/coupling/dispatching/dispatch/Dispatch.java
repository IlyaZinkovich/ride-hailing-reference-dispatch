package io.coupling.dispatching.dispatch;

import io.coupling.dispatching.location.Location;

public class Dispatch {

  final long userId;
  final long bookingId;
  final Location location;

  Dispatch(final long userId, final long bookingId, final Location location) {
    this.userId = userId;
    this.bookingId = bookingId;
    this.location = location;
  }
}
