package com.gmail.amaarquadri.beast.connectr.server;

import com.gmail.amaarquadri.beast.connectr.server.logic.LocationData;
import com.gmail.amaarquadri.beast.connectr.server.logic.ServerRequest;
import com.gmail.amaarquadri.beast.connectr.server.logic.ServerResponse;
import com.gmail.amaarquadri.beast.connectr.server.logic.User;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * Created by amaar on 2018-01-27.
 */
public class ServerRequestHandler {
    private static ArrayList<User> users = new ArrayList<>();
    private static Random random = new Random();

    public static ServerResponse handle(ServerRequest serverRequest) {
        String username;
        String password;
        String newFriendUsername;
        User user;
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
                if (!user.getPassword().equals(password)) return new ServerResponse(ServerResponse.Type.LOGIN_FAILED);
                return ServerResponse.createLoginResultServerResponseSuccess(user);
            case ADD_FRIEND:
                user = serverRequest.getUser();
                newFriendUsername = serverRequest.getNewFriendUsername();

            default: throw new UnsupportedOperationException();
        }
    }

    private static int getRandomId() {
        int id = random.nextInt();
        if (users.stream().anyMatch(user -> user.getId() == id)) return getRandomId();
        return id;
    }
}
