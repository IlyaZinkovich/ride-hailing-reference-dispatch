package io.coupling.dispatch;

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
