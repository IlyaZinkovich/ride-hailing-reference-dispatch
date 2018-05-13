package io.coupling.dispatching.dispatch;

public class Booking {

  final long bookingId;
  final long userId;

  Booking(final long bookingId, final long userId) {
    this.bookingId = bookingId;
    this.userId = userId;
  }
}
