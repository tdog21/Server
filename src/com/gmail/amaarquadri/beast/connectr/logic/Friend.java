package com.gmail.amaarquadri.beast.connectr.logic;

import java.io.Serializable;

/**
 * Created by amandamorin on 2018-01-27.
 */
public class Friend implements Serializable  {
    private final String friendName;
    private LocationData locationData;
    private boolean havePermission;
    private boolean permissionPending;

    public Friend(String friendName, LocationData locationData) {
        this.friendName = friendName;
        this.locationData = locationData;
        havePermission = false;
        permissionPending = false;
    }

    public String getFriendName() {
        return friendName;
    }

    public LocationData getLocationData() {
        return locationData;
    }

    public boolean isHavePermission() {
        return havePermission;
    }

    public boolean isPermissionPending() {
        return permissionPending;
    }
}
