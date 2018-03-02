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
    static String IPAddress = "172.17.1.243";
    static int PortServer = 9999;

    public static void main(String[] args) throws Exception {

        try {
            // creating the socket and the input/output streams
            socket = new Socket(IPAddress, PortServer);
            toServer = new DataOutputStream(socket.getOutputStream());
            InputStreamReader dataInputStream = new InputStreamReader(socket.getInputStream());
            fromServer = new BufferedReader(dataInputStream);

            // a big fat loop to spam the server
            while(true) {
                // we send a request
                sendRequest();
                // as long as we don't get a proper response to our request, we wait
                while(!fromServer.readLine().contains("_type")){
                    if (receiveResponse())
                        break;
                }
            }
        }catch(Exception e){
                // we send a message to notify the server and then we close the socket and the streams
                toServer.writeBytes("."+ '\n');
                socket.close();
                toServer.close();
                fromServer.close();
                System.err.println("Exception: " + e.toString()+ '\n');
            }
        }

    // method to send request to the server
    private static void sendRequest() throws IOException {
        // we create a new stock object
        Stocks sto = new Stocks();
        // we initialize it with random data
        sto.createRandomStock();
        // we display it in the terminal
        System.out.println("--->Trader request: " + Stocks.Serialize(sto)+ '\n');
        // we send a json serialized message to the server
        toServer.writeBytes(Stocks.Serialize(sto)+ '\n');
    }

    // function to handle server's responses
    private static boolean receiveResponse() throws IOException {
        // boolean used to break or not the loop
        boolean test = false;
        // we get the server response
        String serverResponse = fromServer.readLine();
        // we display it in the terminal
        user.output("--->Server answers: " + serverResponse + '\n');
        // if the response is actually a match, we break the loop
        if (!serverResponse.equals("wait")){
            try{
                // we deserialize the json message sent by the server
                Stocks.Deserialize(serverResponse);
                user.output("---> MATCH <---" + '\n');
                test = true;
            }catch(Exception e){
                System.out.println("Exception: " + e.toString()+ '\n');
                //e.printStackTrace();
            }
        }
        return test;
    }
}
