package io.codeforall.bootcamp;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket socket;
    private List<PrintWriter> clientWriters;
    private PrintWriter out;

    private String username;
    private String[] commandChecker;

    public ClientHandler(Socket socket, List<PrintWriter> clientWriters) {
        this.socket = socket;
        this.clientWriters = clientWriters;
    }

    @Override
    public void run() {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Choose an Username: ");

            username = in.readLine();
            out.println("You can now start to chat");

            out.flush();

            clientWriters.add(out);

            String message;

            while ((message = in.readLine()) != null) {
                if(message.startsWith("/")) {
                    commandChecker = message.split(" ", 3);
                } else {
                    broadcast(" > [" + username + "]: " + message);
                }
            }

        } catch (IOException e) {
            System.out.println("Client disconnected " + socket);

        } finally {
            if (out != null) {
                clientWriters.remove(out);
            }
            try {
                socket.close();

            } catch (IOException e) {
                System.out.println("Error: Unable to close client socket");
                e.printStackTrace();
            }
        }
    }

    private void broadcast(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }

    private void whisper(String message) {

    }

//    private enum ChatCommands {
//        W("Use /W + username to write a DM"),
//        EXIT("Use /EXIT to exit the chat"),
//        KICK("Use /KICK + username to kick someone out of the chat");
//
//        private String description;
//
//        ChatCommands(String description) {
//            this.description = description;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//    }
}
