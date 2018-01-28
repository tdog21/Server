package com.gmail.amaarquadri.beast.connectr.server;

import com.gmail.amaarquadri.beast.connectr.server.logic.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * Created by amaar on 2018-01-27.
 */
public class ServerRequestHandler {
    public static final ArrayList<User> users;
    private static Random random = new Random();

    //TODO: confirm this runs before main (though it shouldn't matter)
    static {
        ArrayList<User> temp;
        try {
            temp = DatabaseManager.readFromFile();
        } catch (IOException | ClassNotFoundException ignore) {
            temp = new ArrayList<>();
        }
        users = temp;
    }

    public static ServerResponse handle(ServerRequest serverRequest) {
        String username;
        String password;
        String newFriendUsername;
        User user;
        Friend userFriend;
        User friend;
        Friend myFriend;
        User friendExist;
        boolean isInDatabase;
        synchronized (users) {
            switch (serverRequest.getType()) {
                case CREATE_ACCOUNT:
                    username = serverRequest.getUsername();
                    password = serverRequest.getPassword();
                    if (users.stream().anyMatch(u -> u.getUsername().equals(username)))
                        return new ServerResponse(ServerResponse.Type.LOGIN_FAILED);
                    user = new User(users.size(), getRandomId(), username, password, new LocationData(), new ArrayList<>());
                    users.add(user);
                    return ServerResponse.createLoginResultServerResponseSuccess(user);
                case LOGIN:
                    username = serverRequest.getUsername();
                    password = serverRequest.getPassword();
                    Optional<User> optional = users.stream().filter(u -> u.getUsername().equals(username)).findAny();
                    if (!optional.isPresent()) return new ServerResponse(ServerResponse.Type.LOGIN_FAILED);
                    user = optional.get();
                    if (!user.getPassword().equals(password))
                        return new ServerResponse(ServerResponse.Type.LOGIN_FAILED);
                    return ServerResponse.createLoginResultServerResponseSuccess(user);
                case ADD_FRIEND:
                    user = serverRequest.getUser();
                    newFriendUsername = serverRequest.getNewFriendUsername();
                    isInDatabase = false;
                    User newFriend = null;
                    for(User eachFriend : users)
                    {
                        if(eachFriend.getUsername().equals(newFriendUsername))
                        {
                            isInDatabase = true;
                            newFriend = eachFriend;
                            break;
                        }
                    }
                    if (!isInDatabase)
                    {
                        return new ServerResponse(ServerResponse.Type.ADD_FRIEND_FAILED);
                    }
                    myFriend = new Friend(newFriend);
                    user.getFriends().add(myFriend);
                    newFriend.getFriends().add(new Friend(user));
                    return ServerResponse.createAddFriendServerResponseSuccess(myFriend);
                case ENABLE_PERMISSION:
                    user = serverRequest.getUser();
                    friend = serverRequest.getFriend();
                    isInDatabase = false;
                    friendExist = null;
                    for(User eachFriend : users)
                    {
                        if(eachFriend.getUsername().equals(friend.getUsername()))
                        {
                            isInDatabase = true;
                            friendExist = eachFriend;
                            break;
                        }
                    }
                    if (!isInDatabase)
                    {
                        return new ServerResponse(ServerResponse.Type.ENABLE_PERMISSION_FAILED);
                    }
                    myFriend = new Friend(friendExist);
                    userFriend = new Friend(user);
                    myFriend.setIHavePermission(true); // TODO: this needs to CHANGE!!
                    myFriend.setFriendHasPermission(true);
                    userFriend.setFriendHasPermission(true);
                    userFriend.setIHavePermission(true); // TODO: this needs to CHANGE!!
                    return new ServerResponse(ServerResponse.Type.ENABLE_PERMISSION_SUCCESS);
                case DISABLE_PERMISSION:
                    user = serverRequest.getUser();
                    friend = serverRequest.getFriend();
                    isInDatabase = false;
                    friendExist = null;
                    for(User eachFriend : users)
                    {
                        if(eachFriend.getUsername().equals(friend.getUsername()))
                        {
                            isInDatabase = true;
                            friendExist = eachFriend;
                            break;
                        }
                    }
                    if (!isInDatabase)
                    {
                        return new ServerResponse(ServerResponse.Type.DISABLE_PERMISSION_FAILED);
                    }
                    myFriend = new Friend(friendExist);
                    userFriend = new Friend(user);
                    myFriend.setIHavePermission(false); // TODO: this needs to CHANGE!!
                    myFriend.setFriendHasPermission(false);
                    userFriend.setFriendHasPermission(false);
                    userFriend.setIHavePermission(false); // TODO: this needs to CHANGE!!
                    return new ServerResponse(ServerResponse.Type.DISABLE_PERMISSION_SUCCESS);

                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    private static int getRandomId() {
        int id = random.nextInt();
        if (users.stream().anyMatch(user -> user.getId() == id)) return getRandomId();
        return id;
    }
}
