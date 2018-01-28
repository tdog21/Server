package com.gmail.amaarquadri.beast.connectr.server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


class servertest extends JPanel implements ActionListener {

    static BufferedReader in;
    static PrintWriter out;
    static ServerSocket server;


    private static void listenSocket()

    {

        try {
            server = new ServerSocket(4321);
        } catch (IOException e) {
            System.out.println("Could not listen on port 4321");
            System.exit(-1);
        }

        Socket client = null;
        try {

            client = server.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: 4321");
            System.exit(-1);
        }


        //listenSocketBufferedReaderclientPrintWriter
        try {
            in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(),
                    true);
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                makeandshowGUI();
            }
        });
        listenSocket();

    }


    protected JButton b1;

    public servertest() {
        b1 = new JButton("Send Text");
        b1.setHorizontalTextPosition(AbstractButton.CENTER);
        b1.addActionListener(this);

        add(b1);
        textField.setColumns(20);
        add(textField);
    }

    JTextField textField = new JTextField("");

    private static void makeandshowGUI() {
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        servertest newContentPane = new servertest();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {

        String line;
        while (true) {
            try {
                line = in.readLine();
//Send data back to client
                out.println(line);
                textField.setText(line);
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