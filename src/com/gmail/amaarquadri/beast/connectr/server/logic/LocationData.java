package com.gmail.amaarquadri.beast.connectr.server.logic;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */

public class LocationData implements Serializable {
    private static final long serialVersionUID = 1;

    private final double latitude;
    private final double longitude;
    private final long lastUpdateUnixTime;

    public LocationData(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastUpdateUnixTime = location.getTime();
    }

    public Location toLocation() {
        //TODO: figure out what parameter to pass in
        Location result = new Location("Server");
        result.setLatitude(latitude);
        result.setLongitude(longitude);
        result.setTime(lastUpdateUnixTime);
        return result;
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
