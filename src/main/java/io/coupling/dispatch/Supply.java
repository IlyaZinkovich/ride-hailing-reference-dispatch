package io.coupling.dispatch;

class Supply {

  final long bookingId;
  final Location location;

  Supply(final long bookingId, final Location location) {
    this.bookingId = bookingId;
    this.location = location;
  }
}
