package sample;

import javafx.stage.Screen;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by andrei on 28/03/16.
 */
public final class RequestHandler implements Runnable{
    private Socket socket = null;
    private BufferedReader requestInput = null;
    private DataOutputStream requestOutput = null;
    private File folder;


    public RequestHandler (Socket socket, File folder) {
        this.socket = socket;
        this.folder = folder;
        try {
            requestInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            requestOutput = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server Error while processing new socket\r\n");
            e.printStackTrace();
        }
    }


    public void run () {
        String mainRequestLine = null;
        try {
            mainRequestLine = requestInput.readLine();
            handleRequest(mainRequestLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                requestInput.close();
                requestOutput.close();
                socket.close();
            } catch (IOException e2) {}
        }
    }

    private void handleRequest(String mainRequestLine) throws IOException {
        if (mainRequestLine.contains("DIR")) {
            if (folder.isDirectory()) {
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                    requestOutput.writeChars(listOfFiles[i].getName() + "/n");
                }

                requestOutput.flush();

                requestInput.close();
                requestOutput.close();
            }
        } else if (mainRequestLine.substring(0,5) == "UPLOAD") {
            File upFile = new File(mainRequestLine.substring(7));
            if (!upFile.exists()) {
                FileWriter newFile = new FileWriter(upFile);
                String line;
                while ((line = requestInput.readLine()) != null) {
                    newFile.write(line + "/n");
                }

                newFile.flush();
                newFile.close();

                requestOutput.flush();

                requestInput.close();
                requestOutput.close();
            }

        } else if (mainRequestLine.substring(0,7) == "DOWNLOAD") {
            File downFile = new File(mainRequestLine.substring(9));
            if (downFile.exists()) {
                Scanner readFile = new Scanner(downFile);
                String line;
                while ((line = readFile.nextLine()) != null) {
                    requestOutput.writeChars(line + "/n");
                }

                readFile.close();

                requestOutput.flush();

                requestInput.close();
                requestOutput.close();
            }
        }
    }
}
