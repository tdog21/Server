package com.gmail.amaarquadri.beast.connectr.logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by amaar on 2018-01-27.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1;

    private final int index;
    private final int id;
    private final String username;
    private final String password;
    private LocationData lastLocationData;
    private final ArrayList<Friend> friends;

    public User(int index, int id, String username, String password, LocationData lastLocation,
                ArrayList<Friend> friends) {
        this.index = index;
        this.id = id;
        this.username = username;
        this.password = password;
        this.lastLocationData = lastLocation;
        this.friends = friends;
    }

    public int getIndex() {
        return index;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocationData getLastLocationData() {
        return lastLocationData;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setLastLocationData(LocationData lastLocationData)
    {
        this.lastLocationData = lastLocationData;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;
        if (getIndex() != user.getIndex() || getId() != user.getId() || !getUsername().equals(user.getUsername()) ||
                !getPassword().equals(user.getPassword()) || !getLastLocationData().equals(user.getLastLocationData()) ||
                getFriends().size() != user.getFriends().size()) return false;
        for (int i = 0; i < friends.size(); i++) if (!friends.get(i).equals(user.getFriends().get(i))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getIndex();
        result = 31 * result + getId();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getLastLocationData().hashCode();
        result = 31 * result + getFriends().hashCode();
        return result;
    }
}
