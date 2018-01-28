package com.gmail.amaarquadri.beast.connectr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static BufferedReader in;
    private static PrintWriter out;
    private static ServerSocket server;

    public static void startServer() throws IOException {
        setupServer();
        try {
            String line = in.readLine();
            out.println(line);
            server.close();
            startServer();
        } catch (IOException e) {
            throw new IOException("Read Failed", e);
        }
    }

    private static void setupServer() throws IOException {
        try {
            server = new ServerSocket(4321);
        } catch (IOException e) {
            throw new IOException("Could not listen on port 4321", e);
        }

        Socket client;
        try {
            client = server.accept();
        } catch (IOException e) {
            throw new IOException("Accept failed: 4321", e);
        }

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            throw new IOException("Read failed", e);
        }
    }
}