package com.gmail.amaarquadri.beast.connectr.server.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */

public class ServerResponse implements Serializable {
    public enum Type {
        LOGIN_RESULT, LOGIN_FAILED, ADD_FRIEND_SUCCESS, ADD_FRIEND_FAILED, ENABLE_PERMISSION_FAILED, ENABLE_PERMISSION_SUCCESS
    }

    private final Type type;
    private final User user;
    private final Friend newFriend;

    public ServerResponse(Type type) {
        this.type = type;
        user = null;
        newFriend = null;
    }

    private ServerResponse(Type type, User user, Friend newFriend) {
        this.type = type;
        this.user = user;
        this.newFriend = newFriend;
    }



    public static ServerResponse createLoginResultServerResponseSuccess(User user) {
        return new ServerResponse(Type.LOGIN_RESULT, user, null);
    }

    public static ServerResponse createAddFriendServerResponseSuccess(Friend newFriend) {
        return new ServerResponse(Type.ADD_FRIEND_SUCCESS, null, newFriend);
    }

    public static ServerResponse createEnablePermissionServerResponseSuccess(User user, Friend friend)
    {
        return new ServerResponse(Type.ENABLE_PERMISSION_SUCCESS, user, friend);
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
}
