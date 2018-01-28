package com.gmail.amaarquadri.beast.connectr.server;

import com.gmail.amaarquadri.beast.connectr.server.logic.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by amaar on 2018-01-27.
 */
public class DatabaseManager {
    private static boolean stop = false;

    private DatabaseManager() {
        throw new InternalError("Cannot instantiate DatabaseManager");
    }

    public static void startDatabaseWriter() {
        new Thread(() -> {
            if (stop) return;
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

    public static void stop() {
        stop = true;
    }

    public static void writeToFile(ArrayList<User> users) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("database.ser"))) {
            outputStream.writeObject(users);
        }
    }

    public static ArrayList<User> readFromFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("database.ser"))) {
            return (ArrayList<User>) inputStream.readObject();
        }
    }
}
