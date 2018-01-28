package com.gmail.amaarquadri.beast.connectr.server.logic;

import java.io.Serializable;

/**
 * Created by amaar on 2018-01-27.
 */

public class ServerRequest implements Serializable {
    public enum Type {
        LOGIN, ADD_FRIEND;
    }

    private final Type type;
    private final String username;
    private final String password;
    private final String newFriendUsername;

    private ServerRequest(Type type, String s1, String s2) {
        if (type == Type.LOGIN) {
            this.type = Type.LOGIN;
            username = s1;
            password = s2;
            newFriendUsername = null;
        }
        else if (type == Type.ADD_FRIEND) {
            this.type = Type.ADD_FRIEND;
            username = s1;
            password = null;
            newFriendUsername = s2;
        }
        else throw new UnsupportedOperationException();
    }

    public static ServerRequest createLoginServerRequest(String username, String password) {
        return new ServerRequest(Type.LOGIN, username, password);
    }

    public static ServerRequest createAddFriendServerRequest(String username, String newFriendUsername) {
        return new ServerRequest(Type.ADD_FRIEND, username, newFriendUsername);
    }
}
