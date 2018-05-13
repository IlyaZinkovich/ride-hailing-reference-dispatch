package io.coupling.dispatching.notify;

public class MakeOffer {

  final long bookingId;
  final long driverId;

  public MakeOffer(final long bookingId, final long driverId) {
    this.bookingId = bookingId;
    this.driverId = driverId;
  }
}
