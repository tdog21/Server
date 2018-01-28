package com.gmail.amaarquadri.beast.connectr.server.logic;

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

    private ServerRequest(Type type, User user, Friend friend, String s1, String s2, LocationData location) {
        if (type == Type.CREATE_ACCOUNT) {
            this.type = Type.CREATE_ACCOUNT;
            this.user = null;
            this.friend = null;
            username = s1;
            password = s2;
            newFriendUsername = null;
            locationData = null;
        }
        else if (type == Type.LOGIN) {
            this.type = Type.LOGIN;
            this.user = null;
            this.friend = null;
            username = s1;
            password = s2;
            newFriendUsername = null;
            locationData = null;
        }
        else if (type == Type.ADD_FRIEND) {
            this.type = Type.ADD_FRIEND;
            this.user = user;
            this.friend = null;
            username = null;
            password = null;
            newFriendUsername = s1;
            locationData = null;
        }
        else if (type == Type.ENABLE_PERMISSION) {
            this.type = Type.ENABLE_PERMISSION;
            this.user = user;
            this. friend = friend;
            username = null;
            password = null;
            newFriendUsername = null;
            locationData = null;
        }
        else if (type == Type.DISABLE_PERMISSION) {
            this.type = Type.DISABLE_PERMISSION;
            this.user = user;
            this. friend = friend;
            username = null;
            password = null;
            newFriendUsername = null;
            locationData = null;
        }
        else if (type == Type.GET_LOCATION)
        {
            this.type = Type.GET_LOCATION;
            this.user = user;
            this.friend = friend;
            username = null;
            password = null;
            newFriendUsername = null;
            locationData = location;
        }
        else if (type == Type.UPDATE_LOCATION)
        {
            this.type = Type.UPDATE_LOCATION;
            this.user = user;
            this.friend = friend;
            username = null;
            password = null;
            newFriendUsername = null;
            locationData = location;
        }
        else throw new UnsupportedOperationException();
    }

    public static ServerRequest createCreateAccountServerRequest(String username, String password) {
        return new ServerRequest(Type.CREATE_ACCOUNT, null, null, username, password, null);
    }

    public static ServerRequest createLoginServerRequest(String username, String password) {
        return new ServerRequest(Type.LOGIN, null, null, username, password, null);
    }

    public static ServerRequest createAddFriendServerRequest(User user, String newFriendUsername) {
        return new ServerRequest(Type.ADD_FRIEND, user, null, newFriendUsername, null, null);
    }

    public static ServerRequest createEnablePermissionServerRequest(User user, Friend friend) {
        return new ServerRequest(Type.ENABLE_PERMISSION, user, friend, null, null, null);
    }

    public static ServerRequest createDisablePermissionServerRequest(User user, Friend friend) {
        return new ServerRequest(Type.DISABLE_PERMISSION, user, friend, null, null, null);
    }

    public Type getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public Friend getFriend() {
        return friend;
    }

    public LocationData getLocationData() {
        return locationData;
    }

    public String getPassword() {
        return password;
    }

    public String getNewFriendUsername() {
        return newFriendUsername;
    }
}
