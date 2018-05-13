package io.coupling.dispatching.sort;

import java.util.List;

public class SortedDrivers {

  public final long bookingId;
  public final List<Long> sortedDriverIds;

  public SortedDrivers(final long bookingId, final List<Long> sortedDriverIds) {
    this.bookingId = bookingId;
    this.sortedDriverIds = sortedDriverIds;
  }
}
