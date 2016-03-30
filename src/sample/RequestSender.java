package sample;

import java.io.*;
import java.net.Socket;

/**
 * Created by jude on 28/03/16.
 */
public class ButtonAction {
    private Socket socket;
    private String hostName;
    private int portNumber;
    private BufferedReader in;
    private PrintWriter out;

    public ButtonAction(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public void download(String path) {
        try {
            socket = new Socket(hostName, portNumber);
            File temp = new File(path);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            String request = "DOWNLOAD " + path + " HTTP/1.0";
            String header = "Host: " + hostName;
            String delim = "\n";
            out.print(request + delim + header + delim + delim);
            out.flush();

            String response;
            FileWriter fstream = new FileWriter("./resoruces/" + temp.getName(), true);
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
}
