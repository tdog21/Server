package com.gmail.amaarquadri.beast.connectr.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */
public class ServerRequest implements Serializable {
    public enum Type {
        CREATE_ACCOUNT, LOGIN, ADD_FRIEND, ENABLE_PERMISSION, DISABLE_PERMISSION, UPDATE_LOCATION, GET_LOCATION;
    }

    private final Type type;
    private final User user;
    private final Friend friend;
    private final String username;
    private final String password;
    private final String newFriendUsername;
    private final LocationData locationData;

    private ServerRequest(Type type, User user, Friend friend, String username, String password, String newFriendUsername, LocationData locationData) {
        this.type = type;
        this.user = user;
        this.friend = friend;
        this.username = username;
        this.password = password;
        this.newFriendUsername = newFriendUsername;
        this.locationData = locationData;
    }

    public static ServerRequest createCreateAccountServerRequest(String username, String password) {
        return new ServerRequest(Type.CREATE_ACCOUNT, null, null, username, password, null, null);
    }

    public static ServerRequest createLoginServerRequest(String username, String password) {
        return new ServerRequest(Type.LOGIN, null, null, username, password, null, null);
    }

    public static ServerRequest createAddFriendServerRequest(User user, String newFriendUsername) {
        return new ServerRequest(Type.ADD_FRIEND, user, null, null, null, newFriendUsername, null);
    }

    public static ServerRequest createEnablePermissionServerRequest(User user, Friend friend) {
        return new ServerRequest(Type.ENABLE_PERMISSION, user, friend, null, null, null, null);
    }

    public static ServerRequest createDisablePermissionServerRequest(User user, Friend friend) {
        return new ServerRequest(Type.DISABLE_PERMISSION, user, friend, null, null, null, null);
    }

    public static ServerRequest createUpdateLocationServerRequest(User user, LocationData locationData) {
        return new ServerRequest(Type.UPDATE_LOCATION, user, null, null, null, null, locationData);
    }

    public static ServerRequest createGetLocationServerRequest(User user, Friend friend) {
        return new ServerRequest(Type.GET_LOCATION, user, friend, null, null, null, null);
    }

    public Type getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public Friend getFriend() {
        return friend;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNewFriendUsername() {
        return newFriendUsername;
    }

    public LocationData getLocationData() {
        return locationData;
    }
}
