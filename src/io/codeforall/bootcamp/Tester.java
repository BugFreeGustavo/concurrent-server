package io.codeforall.bootcamp;

import java.util.jar.JarOutputStream;

public class Tester {

    public static void main(String[] args) {

        String message = "/W USERNAME this is a message";

        String[] splitter = message.split(" ", 3);

        System.out.println(splitter[0]);
        System.out.println(splitter[1]);
        System.out.println(splitter[2]);

        System.out.println(message.startsWith("/"));

        System.out.println(splitter[0].substring(1));
    }
}
