package io.codeforall.bootcamp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * ChatServer
 */
public class ChatServer {

    private Socket clientSocket;
    private ServerSocket serverSocket;

    private List<PrintWriter> clientWriters;


    public ChatServer(int port) {

        clientWriters = Collections.synchronizedList(new ArrayList<>());


        try {

            // bind the socket to specified port
            System.out.println("Binding to port " + port);
            serverSocket = new ServerSocket(port);

            System.out.println("Server started: " + serverSocket);

            while (true) {
                // block waiting for a client to connect
                System.out.println("Waiting for a client connection");

                clientSocket = serverSocket.accept();


                // handle client connection
                System.out.println("Client accepted: " + clientSocket);


                ClientHandler clientHandler = new ClientHandler(clientSocket, clientWriters);
                new Thread(clientHandler).start();
            }

        } catch (IOException ioe) {

            System.out.println(ioe.getMessage());

        } finally {

            close();

        }
    }

    /**
     * ChatServer entry point
     *
     * @param args ChatServer port number
     */
    public static void main(String args[]) {

        // exit application if no port number is specified
        if (args.length == 0) {
            System.out.println("Usage: java ChatServer [port]");
            System.exit(1);
        }

        try {
            // try to create an instance of the ChatServer at port specified at args[0]
            new ChatServer(Integer.parseInt(args[0]));

        } catch (NumberFormatException ex) {
            // write an error message if an invalid port was specified by the user
            System.out.println("Invalid port number " + args[0]);
        }

    }

    /**
     * Closes the client socket and the buffered input reader
     */
    public void close() {

        try {

            if (clientSocket != null) {
                System.out.println("Closing client connection");
                clientSocket.close();
            }

            if (serverSocket != null) {
                System.out.println("Closing server socket");
                serverSocket.close();
            }


        } catch (IOException ex) {

            System.out.println("Error closing connection: " + ex.getMessage());

        }

    }
}
