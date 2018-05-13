package io.coupling.dispatching.location;

import static java.lang.String.format;

public class Location {

  private final double lat;
  private final double lng;

  public Location(final double lat, final double lng) {
    this.lat = lat;
    this.lng = lng;
  }

  public String toGeoHash() {
    return format("%f_%f", lat, lng);
  }
}
