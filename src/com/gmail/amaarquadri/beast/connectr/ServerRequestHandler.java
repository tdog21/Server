package com.gmail.amaarquadri.beast.connectr;

import com.gmail.amaarquadri.beast.connectr.logic.*;

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
        Friend friendExist;
        boolean isInDatabase;
        /*------------------------------------------------------------------------------------------------------------*/
        synchronized (users) {
            switch (serverRequest.getType()) {
                case CREATE_ACCOUNT:
                    username = serverRequest.getUsername();
                    password = serverRequest.getPassword();
                    if (users.stream().anyMatch(u -> u.getUsername().equals(username)))
                        return ServerResponse.FAILED;
                    user = new User(users.size(), getRandomId(), username, password, new LocationData(), new ArrayList<>());
                    users.add(user);
                    return ServerResponse.createLoginResultServerResponse(user);
                /*----------------------------------------------------------------------------------------------------*/
                case LOGIN:
                    username = serverRequest.getUsername();
                    password = serverRequest.getPassword();
                    Optional<User> optional = users.stream().filter(u -> u.getUsername().equals(username)).findAny();
                    if (!optional.isPresent()) return ServerResponse.FAILED;
                    user = optional.get();
                    if (!user.getPassword().equals(password)) return ServerResponse.FAILED;
                    return ServerResponse.createLoginResultServerResponse(user);
                /*----------------------------------------------------------------------------------------------------*/
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
                    if (!isInDatabase) return ServerResponse.FAILED;
                    myFriend = new Friend(newFriend);
                    user.getFriends().add(myFriend);
                    newFriend.getFriends().add(new Friend(user));
                    return ServerResponse.createAddFriendResultServerResponse(myFriend);
                /*----------------------------------------------------------------------------------------------------*/
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
                            friendExist = new Friend(eachFriend);
                            break;
                        }
                    }
                    if (!isInDatabase) return ServerResponse.FAILED;
                    userFriend = new Friend(user);
                    friendExist.setIHavePermission(true); // TODO: this needs to CHANGE!!
                    friendExist.setFriendHasPermission(true);
                    userFriend.setFriendHasPermission(true);
                    userFriend.setIHavePermission(true); // TODO: this needs to CHANGE!!
                    return ServerResponse.SUCCESS;
                /*----------------------------------------------------------------------------------------------------*/
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
                            friendExist = new Friend(eachFriend);
                            break;
                        }
                    }
                    if (!isInDatabase) return ServerResponse.FAILED;
                    userFriend = new Friend(user);
                    friendExist.setIHavePermission(false); // TODO: this needs to CHANGE!!
                    friendExist.setFriendHasPermission(false);
                    userFriend.setFriendHasPermission(false);
                    userFriend.setIHavePermission(false); // TODO: this needs to CHANGE!!
                    return ServerResponse.SUCCESS;
                /*----------------------------------------------------------------------------------------------------*/
                case GET_LOCATION:
                    friend = serverRequest.getFriend();
                    isInDatabase = false;
                    friendExist = null;
                    for(User eachFriend : users) {
                        if(eachFriend.getUsername().equals(friend.getUsername())) {
                            isInDatabase = true;
                            friendExist = new Friend(eachFriend);
                            break;
                        }
                    }
                    if (!isInDatabase) return ServerResponse.FAILED;
                    if(!friendExist.iHavePermission()) return ServerResponse.FAILED;
                    return ServerResponse.createGetLocationResultServerResponse(friendExist.getLastLocationData());
                /*----------------------------------------------------------------------------------------------------*/
                case UPDATE_LOCATION:
                    user = serverRequest.getUser();
                    user.setLastLocationData(serverRequest.getLocationData());
                    return ServerResponse.SUCCESS;

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
