package com.gmail.amaarquadri.beast.connectr.server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//https://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string
class serverTest{
    private static BufferedReader in;
    private static PrintWriter out;
    private static ServerSocket server;

    public static void main(String[] args) {
        listenSocket();
    }

    private static void listenSocket() {
        try {
            server = new ServerSocket(4321);
        } catch (IOException ignore) {
            System.out.println("Could not listen on port 4321");
            System.exit(-1);
        }

        Socket client = null;
        try {
            client = server.accept();
        } catch (IOException ignore) {
            System.out.println("Accept failed: 4321");
            System.exit(-1);
        }

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ignore) {
            System.out.println("Read failed");
            System.exit(-1);
        }
    }

    public void dothething() {

        String line;
        while (true) {
            try {
                line = in.readLine();
//Send data back to client
                out.println(line);
                server.close();
                break;
            } catch (IOException g) {
                System.out.println("Read failed");
                System.exit(-1);
            }
        }
        listenSocket();
    }
}