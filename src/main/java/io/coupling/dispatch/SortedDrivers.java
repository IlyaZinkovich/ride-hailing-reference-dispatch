package io.coupling.dispatch;

import java.util.List;

public class SortedDrivers {

  final long bookingId;
  final List<Long> sortedDriverIds;

  SortedDrivers(final long bookingId, final List<Long> sortedDriverIds) {
    this.bookingId = bookingId;
    this.sortedDriverIds = sortedDriverIds;
  }
}
