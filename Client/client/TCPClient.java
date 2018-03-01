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
            socket = new Socket("localhost", 9999);
            toServer = new DataOutputStream(socket.getOutputStream());
            InputStreamReader dataInputStream = new InputStreamReader(socket.getInputStream());
            fromServer = new BufferedReader(dataInputStream);
            do {
                for (int i = 0; i < 10; i++) {
                    sendRequest();
                    receiveResponse();
                }

            } while (true);/*
            toServer.writeBytes("."+ '\n');
            socket.close();
            toServer.close();
            fromServer.close();*/
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString());
        }
    }

    private static void sendRequest() throws IOException {
        Stocks sto = new Stocks();
        sto.createRandomStock();
        System.out.println(Stocks.Serialize(sto));
        toServer.writeBytes(Stocks.Serialize(sto) + '\n');
    }

    private static boolean receiveResponse() throws IOException {
        boolean test = true;
        String serverResponse = fromServer.readLine();
        user.output("--->Server answers: " + serverResponse + '\n');
        if (!serverResponse.equals("wait")) {
            try {
                Stocks.Deserialize(serverResponse);
                user.output("---> MATCH <---");
                test = false;
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        return test;
    }
}
