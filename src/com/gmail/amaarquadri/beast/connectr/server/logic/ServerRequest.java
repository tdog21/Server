package com.gmail.amaarquadri.beast.connectr.server.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */

public class ServerRequest implements Serializable {
    public enum Type {
        CREATE_ACCOUNT, LOGIN, ADD_FRIEND;
    }

    private final Type type;
    private final User user;
    private final String username;
    private final String password;
    private final String newFriendUsername;

    private ServerRequest(Type type, User user, String s1, String s2) {
        if (type == Type.CREATE_ACCOUNT) {
            this.type = Type.CREATE_ACCOUNT;
            this.user = null;
            username = s1;
            password = s2;
            newFriendUsername = null;
        }
        else if (type == Type.LOGIN) {
            this.type = Type.LOGIN;
            this.user = null;
            username = s1;
            password = s2;
            newFriendUsername = null;
        }
        else if (type == Type.ADD_FRIEND) {
            this.type = Type.ADD_FRIEND;
            this.user = user;
            username = null;
            password = null;
            newFriendUsername = s1;
        }
        else throw new UnsupportedOperationException();
    }

    public static ServerRequest createLoginServerRequest(String username, String password) {
        return new ServerRequest(Type.LOGIN, null, username, password);
    }

    public static ServerRequest createAddFriendServerRequest(User user, String newFriendUsername) {
        return new ServerRequest(Type.ADD_FRIEND, user, newFriendUsername, null);
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

    public String getPassword() {
        return password;
    }

    public String getNewFriendUsername() {
        return newFriendUsername;
    }
}
