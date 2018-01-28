package com.gmail.amaarquadri.beast.connectr.server.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */
public class LocationData implements Serializable {
    private static final long serialVersionUID = 1;

    private final double latitude;
    private final double longitude;
    private final long lastUpdateUnixTime;

    public LocationData(double latitude, double longitude, long lastUpdateUnixTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdateUnixTime = lastUpdateUnixTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLastUpdateUnixTime() {
        return lastUpdateUnixTime;
    }
}
