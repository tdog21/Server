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
    private static ServerSocket server;

    public static void startServer() throws IOException, ClassNotFoundException {
        if (stop) return;
        setupServer();
        ServerRequest request=null;
            try {
                out.writeObject(ServerRequestHandler.handle(request));
                System.out.println("server responded");
                server.close();
                System.out.println("server closed");
                startServer();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("Read Failed", e);
            }
        }
    }

    public static void stop() {
        stop = true;
    }

    private static void setupServer() throws IOException {
        try {
            server = new ServerSocket(4321);
            System.out.println("serverSocket active");
        } catch (IOException e) {
            throw new IOException("Could not listen on port 4321", e);
        }

        Socket client;
        try {
            client = server.accept();
            System.out.println("looking for requests");
        } catch (IOException e) {
            throw new IOException("Accept failed: 4321", e);
        }

        try {
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
            System.out.println("I/O initialized");
        } catch (IOException e) {
            throw new IOException("Read failed", e);
        }
        System.out.println("Server setup finished");
    }
}