package com.gmail.amaarquadri.beast.connectr.server.logic;

/**
 * Created by amandamorin on 2018-01-27.
 */
public class Friend extends User {
    private final boolean iHavePermission;
    private final boolean friendHasPermission;

    public Friend(int index, int id, String username, String password, LocationData lastLocation,
                  boolean iHavePermission, boolean friendHasPermission) {
        super(index, id, username, password, lastLocation, null);
        this.iHavePermission = iHavePermission;
        this.friendHasPermission = friendHasPermission;
    }

    public boolean iHavePermission() {
        return iHavePermission;
    }

    public boolean friendHasPermission() {
        return friendHasPermission;
    }
}
