package client;

/*
 * 22. 10. 10
 */

/**
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class TCPClient {

    static String line;
    static Socket socket;
    static BufferedReader fromServer;
    static DataOutputStream toServer;
    static UserInterface user = new UserInterface();

    public static void main(String[] args) throws Exception {
        socket = new Socket("172.17.2.0", 9999);
        toServer = new DataOutputStream(     // Datastream FROM Server
                socket.getOutputStream());
        fromServer = new BufferedReader(     // Datastream TO Server
                new InputStreamReader(socket.getInputStream()));
        while (sendRequest()) {              // Send requests while connected
            receiveResponse();                 // Process server's answer
        }
        socket.close();
        toServer.close();
        fromServer.close();
    }

    private static boolean sendRequest() throws IOException {
        boolean holdTheLine = true;          // Connection exists
    

        toServer.writeBytes((line = user.input()) + '\n');

        return holdTheLine;
    }

    private static void receiveResponse() throws IOException {
        user.output("Server answers: " +
                new String(fromServer.readLine()) + '\n');
    }
}
