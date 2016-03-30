package sample;

import java.io.*;
import java.net.*;

public final class Server {
    private ServerSocket serverSocket = null;
    private File folder = null;

    public Server(int port, File folder) throws IOException {
        serverSocket = new ServerSocket(port);
        this.folder = folder;
    }

    public void handleRequests() throws IOException {
        System.out.println("Simple File Sharing Server v1.0");

        // repeatedly handle incoming requests
        while(true) {
            Socket socket = serverSocket.accept();
            Thread handlerThread = new Thread(new RequestHandler(socket, folder));
            handlerThread.start();
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        try {
            File folder = new File ("./shared_files");
            if (!folder.exists()) {
                folder.mkdir();
            }
            Server server = new Server(port, folder);
            server.handleRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

