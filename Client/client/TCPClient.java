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

import org.json.;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

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

        /*List<Client> clients = new ArrayList<Client>();
        Client cl = new Client();
        cl.id_client = 1;
        cl.name = "Mohamed";

        for (Client cl: clients) {

        }*/

        Stocks sto = new Stocks();
        sto._type = StockType.Bids;
        sto._code = "123";
        sto._amount = 10;
        sto._unitPrice = 1;

        Gson obj = new Gson();
        obj.toJSon(sto);

        toServer.writeBytes(obj.tostring() + '\n');

        return holdTheLine;
    }

    private static void receiveResponse() throws IOException {
        user.output("Server answers: " +
                new String(fromServer.readLine()) + '\n');
    }
}
