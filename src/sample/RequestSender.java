package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by jude on 28/03/16.
 */
public class RequestSender {
    private Socket socket;
    private String hostName;
    private int portNumber;
    private BufferedReader in;
    private PrintWriter out;
    private String request;
    private String localDir;
    private ObservableList<String> fileList;

    public RequestSender(String hostName, int portNumber, String localDir) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.localDir = localDir;
        fileList = FXCollections.observableArrayList();

        try {
            socket = new Socket(this.hostName, this.portNumber);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

    public void download(String path) {
        try {
            File temp = new File(path);
            if (!temp.exists()) {
                System.out.println("File does not e");
            }

            request = "DOWNLOAD " + path;
            String delim = "\r\n";
            out.print(request + delim);
            out.flush();

            String response;
            FileWriter fstream = new FileWriter(localDir + temp.getName(), true);
            BufferedWriter transfer = new BufferedWriter(fstream);
            System.out.println("Response:");
            while ((response = in.readLine()) != null) {
                transfer.write(response);
                transfer.newLine();
            }
            transfer.close();

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void upload(String file) {
        try {
            socket = new Socket(hostName, portNumber);
            File temp = new File(file);

            request = "UPLOAD " + file;
            String delim = "\r\n";
            out.print(request + delim);
            out.flush();

            if (temp.exists()) {
                Scanner readFile = new Scanner(temp);
                String line;
                while ((line = readFile.nextLine()) != null) {
                    out.print(line + delim);
                }

                readFile.close();

                out.flush();

                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public ObservableList<String> dir() {
        try {
            // send the HTTP request GET /yahoo/yahoo.html HTTP/1.0\n\n
            String request = "DIR";
            String delim = "\r\n";
            out.print(request + delim );
            out.flush();

            // read and print the response
            String response;
            System.out.println("Response:");
            while ((response = in.readLine()) != null) {
                fileList.add(response);
                System.out.println(response);
            }

            // close the connection (3-way tear down handshake)
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return fileList;
    }
}
