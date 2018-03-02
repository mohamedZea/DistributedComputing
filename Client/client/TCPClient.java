package client;

import com.google.gson.Gson;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TCPClient {
    //static boolean waiting;
    static String line;
    static Socket socket;
    static BufferedReader fromServer;
    static DataOutputStream toServer;
    static UserInterface user = new UserInterface();

    public static void main(String[] args) throws Exception {

        try {
            // creating the socket and the input/output streams
            socket = new Socket("localhost", 9999);
            toServer = new DataOutputStream(socket.getOutputStream());
            InputStreamReader dataInputStream = new InputStreamReader(socket.getInputStream());
            fromServer = new BufferedReader(dataInputStream);
            // a big fat loop to spam the server
            while (true) {
                for (int i = 0; i < 10; i++) {
                    sendRequest();
                    while (!fromServer.readLine().contains("_type")) {
                        if (receiveResponse())
                            break;
                    }
                }
            }
            /*
            toServer.writeBytes("."+ '\n');
            socket.close();
            toServer.close();
            fromServer.close();*/
        } catch (Exception e) {
            toServer.writeBytes("." + '\n');
            socket.close();
            toServer.close();
            fromServer.close();
            System.err.println("Exception: " + e.toString());
        }
    }

    private static void sendRequest() throws IOException {
        Stocks sto = new Stocks();
        Stocks sto2 = new Stocks();
        // random stock request creation
        sto.createRandomStock();

        // we display it in the terminal
        System.out.println("--->Trader request: " + Stocks.Serialize(sto));
        // we send it to the server
        toServer.writeBytes(Stocks.Serialize(sto) + '\n');
    }

    private static boolean receiveResponse() throws IOException {
        boolean test = false;
        // we get the server response
        String serverResponse = fromServer.readLine();
        // we display it in the terminal
        user.output("--->Server answers: " + serverResponse + '\n');
        // if the response is actually a match
        if (!serverResponse.equals("wait")) {
            try {
                Stocks.Deserialize(serverResponse);
                user.output("---> MATCH <---");
                test = true;
            } catch (Exception e) {
                System.out.println("Exception: " + e.toString());
                //e.printStackTrace();
            }
        }
        return test;
    }
}
