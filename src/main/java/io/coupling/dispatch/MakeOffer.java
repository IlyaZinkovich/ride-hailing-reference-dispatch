package io.coupling.dispatch;

public class MakeOffer {

  final long bookingId;
  final long driverId;

  MakeOffer(final long bookingId, final long driverId) {
    this.bookingId = bookingId;
    this.driverId = driverId;
  }
}
