package com.gmail.amaarquadri.beast.connectr.server.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */

public class ServerResponse implements Serializable {
    public enum Type {
        LOGIN_RESULT, LOGIN_FAILED, ADD_FRIEND_SUCCESS, ADD_FRIEND_FAILED, ENABLE_PERMISSION_FAILED, ENABLE_PERMISSION_SUCCESS,
            DISABLE_PERMISSION_FAILED, DISABLE_PERMISSION_SUCCESS, GET_LOCATION_FAILED, GET_LOCATION_SUCCESS
    }

    private final Type type;
    private final User user;
    private final Friend newFriend;
    private final LocationData locationData;

    public ServerResponse(Type type) {
        this.type = type;
        user = null;
        newFriend = null;
        locationData = null;
    }

    private ServerResponse(Type type, User user, Friend newFriend, LocationData locationData) {
        this.type = type;
        this.user = user;
        this.newFriend = newFriend;
        this.locationData = locationData;
    }



    public static ServerResponse createLoginResultServerResponseSuccess(User user) {
        return new ServerResponse(Type.LOGIN_RESULT, user, null, null);
    }

    public static ServerResponse createAddFriendServerResponseSuccess(Friend newFriend) {
        return new ServerResponse(Type.ADD_FRIEND_SUCCESS, null, newFriend, null);
    }

    public static ServerResponse createGetLocationServerResponseSuccess(LocationData locationData)
    {
        return new ServerResponse(Type.GET_LOCATION_SUCCESS, null, null, locationData);
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
