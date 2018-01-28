package com.gmail.amaarquadri.beast.connectr.logic;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by amaar on 2018-01-27.
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame("Server");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                DatabaseManager.stop();
                Server.stop();
                try {
                    DatabaseManager.writeToFile(ServerRequestHandler.users);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
        DatabaseManager.startDatabaseWriter();
        System.out.println("Database initialized");
        Server.startServer();
        System.out.println("Server Initalized");
    }
}
