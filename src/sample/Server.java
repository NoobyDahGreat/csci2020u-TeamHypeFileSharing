package sample;

import java.io.*;
import java.net.*;

public final class Server {
    private ServerSocket serverSocket = null;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void handleRequests() throws IOException {
        System.out.println("Simple Http Server v1.0: Ready to handle incoming requests.");

        // repeatedly handle incoming requests
        while(true) {
            Socket socket = serverSocket.accept();
            Thread handlerThread = new Thread(new RequestHandler(socket, null)); //added null to get program to run, check what u meant andrei
            handlerThread.start();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        try {
            Server server = new Server(port);
            server.handleRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

