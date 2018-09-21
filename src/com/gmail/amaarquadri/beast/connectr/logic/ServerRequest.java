package com.gmail.amaarquadri.beast.connectr.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */
public class ServerRequest implements Serializable {
    public enum Type {
        CREATE_ACCOUNT, LOGIN, SEND_FRIEND_REQUEST, ACCEPT_FRIEND_REQUEST,
        REQUEST_LOCATION_PERMISSION, GRANT_LOCATION_PERMISSION, REVOKE_LOCATION_PERMISSION,
        UPDATE_LOCATION, GET_LOCATION
    }

    private final Type type;
    private final String username;
    private final String password;
    private final String friendUserName;
    private final LocationData locationData;

    private ServerRequest(Type type, String username, String password, String friendUserName, LocationData locationData) {
        this.type = type;
        this.username = username;
        this.password = password;
        this.friendUserName = friendUserName;
        this.locationData = locationData;
    }

    public static ServerRequest createCreateAccountServerRequest(String username, String password) {
        return new ServerRequest(Type.CREATE_ACCOUNT, username, password, null, null);
    }

    public static ServerRequest createLoginServerRequest(String username, String password) {
        return new ServerRequest(Type.LOGIN, username, password, null, null);
    }

    public static ServerRequest createFriendRequestServerRequest(String username, String friendUsername) {
        return new ServerRequest(Type.SEND_FRIEND_REQUEST, username, null, friendUsername, null);
    }

    public static ServerRequest createEnablePermissionServerRequest(String username, String friendUsername) {
        return new ServerRequest(Type.GRANT_LOCATION_PERMISSION, username, null, friendUsername, null);
    }

    public static ServerRequest createDisablePermissionServerRequest(String user, String friend) {
        return new ServerRequest(Type.REVOKE_LOCATION_PERMISSION, user, null, friend, null);
    }

    public static ServerRequest createUpdateLocationServerRequest(String username, LocationData locationData) {
        return new ServerRequest(Type.UPDATE_LOCATION, username, null, null, locationData);
    }

    public static ServerRequest createGetLocationServerRequest(String  username, String friendUsername) {
        return new ServerRequest(Type.GET_LOCATION, username, null, friendUsername, null);
    }

    public Type getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFriendUsername() {
        return friendUserName;
    }

    public LocationData getLocationData() {
        return locationData;
    }
}
