package com.gmail.amaarquadri.beast.connectr.logic;

/**
 * Created by amandamorin on 2018-01-27.
 */
public class Friend extends User {
    private boolean iHavePermission;
    private boolean friendHasPermission;
    private boolean iAmPendingPermission;
    private boolean friendIsPendingPermission;

    public Friend(User user) {
        super(user.getIndex(), user.getId(), user.getUsername(), user.getPassword(), user.getLastLocationData(), user.getFriends());
        iHavePermission = false;
        friendHasPermission = false;
        iAmPendingPermission = false;
        friendIsPendingPermission = false;
    }

    public boolean iHavePermission() {
        return iHavePermission;
    }

    public boolean friendHasPermission() {
        return friendHasPermission;
    }

    public void setIHavePermission(boolean iHavePermission) {
        this.iHavePermission = iHavePermission;
    }

    public void setFriendHasPermission(boolean friendHasPermission) {
        this.friendHasPermission = friendHasPermission;
    }


    public boolean iAmPendingPermission() {
        return iAmPendingPermission;
    }

    public boolean friendIsPendingPermsission() {
        return friendIsPendingPermission;
    }
}
