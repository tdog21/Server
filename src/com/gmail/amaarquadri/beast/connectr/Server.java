package com.gmail.amaarquadri.beast.connectr;

import com.gmail.amaarquadri.beast.connectr.logic.ServerRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Server() {
        throw new InternalError("Server cannot be instantiated.");
    }

    private static boolean stop = false;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void startServer() throws IOException, ClassNotFoundException {
        System.out.println("Starting server");
        if (stop) return;
        setupServer();
        while (true) out.writeObject(ServerRequestHandler.handle((ServerRequest) in.readObject()));
    }

    public static void stop() {
        System.out.println("Stopping server");
        stop = true;
    }

    private static void setupServer() throws IOException {
        System.out.println("Setting up Server");
        ServerSocket server;
        try {
            System.out.println("serverSocket active");
            server = new ServerSocket(4555);
        } catch (IOException e) {
            throw new IOException("Could not listen on port 4321", e);
        }

        Socket client;
        try {
            System.out.println("looking for requests");
            client = server.accept();
        } catch (IOException e) {
            throw new IOException("Accept failed: 4321", e);
        }

        try {
            System.out.println("Initializing I/O");
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new IOException("Read failed", e);
        }
        System.out.println("Server setup finished");
    }
}