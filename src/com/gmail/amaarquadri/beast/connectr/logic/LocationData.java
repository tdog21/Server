package com.gmail.amaarquadri.beast.connectr.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */
public class LocationData implements Serializable {
    private static final long serialVersionUID = 1;

    private final double latitude;
    private final double longitude;
    //if lastUpdateUnixTime is 0, then no LocationData is available
    private final long lastUpdateUnixTime;

    public LocationData() {
        this(0, 0, 0);
    }

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

    public long getLastUpdateUnixTime() {
        return lastUpdateUnixTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LocationData that = (LocationData) obj;
        return Double.compare(that.getLatitude(), getLatitude()) == 0 &&
                Double.compare(that.getLongitude(), getLongitude()) == 0 &&
                getLastUpdateUnixTime() == that.getLastUpdateUnixTime();
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(getLatitude());
        int result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLongitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (getLastUpdateUnixTime() ^ (getLastUpdateUnixTime() >>> 32));
        return result;
    }
}
