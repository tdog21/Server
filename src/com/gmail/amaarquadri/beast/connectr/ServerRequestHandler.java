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
        synchronized (users) {
            if (serverRequest.getType() == ServerRequest.Type.CREATE_ACCOUNT) {
                String username = serverRequest.getUsername();
                String password = serverRequest.getPassword();
                if (users.stream().anyMatch(u -> u.getUsername().equals(username))) return ServerResponse.FAILED;

                User user = new User(users.size(), getRandomId(), username, password, new LocationData(), new ArrayList<>());
                users.add(user);
                return ServerResponse.createLoginResultServerResponse(user);
            }
            else if (serverRequest.getType() == ServerRequest.Type.LOGIN) {
                String username = serverRequest.getUsername();
                String password = serverRequest.getPassword();

                Optional<User> optional = users.stream().filter(u -> u.getUsername().equals(username)).findAny();
                if (!optional.isPresent()) return ServerResponse.FAILED;

                User user = optional.get();
                if (!user.getPassword().equals(password)) return ServerResponse.FAILED;
                return ServerResponse.createLoginResultServerResponse(user);
            }
            else if (serverRequest.getType() == ServerRequest.Type.SEND_FRIEND_REQUEST) {
                String username = serverRequest.getUsername();
                String newFriendUsername = serverRequest.getFriendUsername();

                Optional<User> optionalUser = users.stream().filter(u -> u.getUsername().equals(username)).findAny();
                if (!optionalUser.isPresent()) return ServerResponse.FAILED;
                Optional<User> optionalFriend = users.stream().filter(u -> u.getUsername().equals(newFriendUsername)).findAny();
                if (!optionalFriend.isPresent()) return ServerResponse.FAILED;

                User user = optionalUser.get();
                User newFriend = optionalFriend.get();
                user.getFriends().add(new Friend(newFriend.getUsername(), newFriend.getLastLocationData())); //TODO: implement friend requests
                newFriend.getFriends().add(new Friend(user.getUsername(), user.getLastLocationData()));
                return ServerResponse.createAddFriendResultServerResponse(user);
            }
            else if (serverRequest.getType() == ServerRequest.Type.ENABLE_LOCATION_PERMISSION) {
                String username = serverRequest.getUsername();
                String friendUsername = serverRequest.getFriendUsername();

                Optional<User> optionalUser = users.stream().filter(u -> u.getUsername().equals(username)).findAny();
                if (!optionalUser.isPresent()) return ServerResponse.FAILED;
                Optional<User> optionalFriend = users.stream().filter(u -> u.getUsername().equals(friendUsername)).findAny();
                if (!optionalFriend.isPresent()) return ServerResponse.FAILED;

                User user = optionalUser.get();
                ///User friend = optionalFriend.get();

                Optional<Friend> friend = user.getFriends().stream().filter(f -> f.getFriendName().equals(friendUsername)).findAny();
                if (!friend.isPresent()) return ServerResponse.FAILED;


                friend.setIHavePermission(true); //TODO: this needs to be removed
                friend.setFriendHasPermission(true);


                userFriend = new Friend(user);
                friendExist.setIHavePermission(true); // TODO: this needs to CHANGE!!
                friendExist.setFriendHasPermission(true);
                userFriend.setFriendHasPermission(true);
                userFriend.setIHavePermission(true); // TODO: this needs to CHANGE!!
                return ServerResponse.SUCCESS;
            }
            else if (serverRequest.getType() == ServerRequest.Type.REVOKE_LOCATION_PERMISSION) {
                user = serverRequest.getUser();
                friend = serverRequest.getFriend();
                isInDatabase = false;
                friendExist = null;
                for (User eachFriend : users) {
                    if (eachFriend.getUsername().equals(friend.getUsername())) {
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
            }
            else if (serverRequest.getType() == ServerRequest.Type.GET_LOCATION) {
                friend = serverRequest.getFriend();
                isInDatabase = false;
                friendExist = null;
                for (User eachFriend : users) {
                    if (eachFriend.getUsername().equals(friend.getUsername())) {
                        isInDatabase = true;
                        friendExist = new Friend(eachFriend);
                        break;
                    }
                }
                if (!isInDatabase) return ServerResponse.FAILED;
                if (!friendExist.iHavePermission()) return ServerResponse.FAILED;
                return ServerResponse.createGetLocationResultServerResponse(friendExist.getLastLocationData());
            }
            else if (serverRequest.getType() == ServerRequest.Type.UPDATE_LOCATION) {
                user = serverRequest.getUser();
                user.setLastLocationData(serverRequest.getLocationData());
                return ServerResponse.SUCCESS;
            }

            else {
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
