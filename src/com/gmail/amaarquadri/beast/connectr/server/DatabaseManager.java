package com.gmail.amaarquadri.beast.connectr.server;

import com.gmail.amaarquadri.beast.connectr.server.logic.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by amaar on 2018-01-27.
 */
public class DatabaseManager {
    private DatabaseManager() {
        throw new InternalError("Cannot instantiate DatabaseManager");
    }

    public void startDatabaseWriter() {
        new Thread(() -> {
            try {
                Thread.sleep(300000);
            } catch (InterruptedException ignore) {}

            synchronized (ServerRequestHandler.users) {
                try {
                    writeToFile(ServerRequestHandler.users);
                } catch (IOException e) {
                    //TODO: handle
                }
            }
        }).start();
    }

    private static void writeToFile(ArrayList<User> users) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("database.ser"));
        outputStream.writeObject(users);
        outputStream.close();
    }

    public static ArrayList<User> readFromFile() {

    }
}
