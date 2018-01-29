package com.gmail.amaarquadri.beast.connectr.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */
public class ServerResponse implements Serializable {
    public static final ServerResponse SUCCESS = new ServerResponse(Type.SUCCESS, null, null, null);
    public static final ServerResponse FAILED = new ServerResponse(Type.FAILED, null, null, null);

    public enum Type {
        SUCCESS, FAILED, LOGIN_RESULT, ADD_FRIEND_RESULT, GET_LOCATION_RESULT
    }

    private final Type type;
    private final User user;
    private final Friend newFriend;
    private final LocationData locationData;

    private ServerResponse(Type type, User user, Friend newFriend, LocationData locationData) {
        this.type = type;
        this.user = user;
        this.newFriend = newFriend;
        this.locationData = locationData;
    }

    public static ServerResponse createLoginResultServerResponse(User user) {
        return new ServerResponse(Type.LOGIN_RESULT, user, null, null);
    }

    public static ServerResponse createAddFriendResultServerResponse(User user) {
        return new ServerResponse(Type.ADD_FRIEND_RESULT, user, null, null);
    }

    public static ServerResponse createGetLocationResultServerResponse(LocationData locationData) {
        return new ServerResponse(Type.GET_LOCATION_RESULT, null, null, locationData);
    }

    public Type getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public Friend getNewFriend() {
        return newFriend;
    }

    public LocationData getLocationData() { return locationData;}
}
