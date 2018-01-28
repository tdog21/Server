package com.gmail.amaarquadri.beast.connectr.server;

import com.gmail.amaarquadri.beast.connectr.server.logic.ServerRequest;
import com.gmail.amaarquadri.beast.connectr.server.logic.ServerResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class Server {
    private Server() {
        throw new InternalError("Server cannot be instantiated.");
    }

    private static BufferedReader in;
    private static PrintWriter out;
    private static ServerSocket server;

    public static void startServer() throws IOException, ClassNotFoundException {
        setupServer();
        try {
            out.println(serializeServerResponse(ServerRequestHandler.handle(deserializeServerRequest(in.readLine()))));
            server.close();
            startServer();
        } catch (IOException e) {
            throw new IOException("Read Failed", e);
        }
        catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Read Failed", e);
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

    private static ServerRequest deserializeServerRequest(String serverRequest) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(serverRequest)));
        ServerRequest request  = (ServerRequest) inputStream.readObject();
        inputStream.close();
        return request;
    }

    private static String serializeServerResponse(ServerResponse serverResponse) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(serverResponse);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }
}