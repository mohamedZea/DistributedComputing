// 22. 10. 10

/**
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class TCPServer {


    public static ArrayList<StockService> ClientsList = new ArrayList<StockService>();
    static StocksManager stockManager = new StocksManager();

    public static void main(String[] args) throws Exception{
        int port = 9999;
        ServerSocket listenSocket = new ServerSocket(port);
        System.out.println("Multithreaded Server starts on Port "+port);
        while (true){
            Socket client = listenSocket.accept();
            System.out.println("Connection with: " +     // Output connection
                    client.getRemoteSocketAddress());   // (Client) address
            StockService s = new StockService(client,stockManager);
            RegisterConnection(s);
            s.start();

        }
    }

    static void RegisterConnection(StockService so)
    {
        if(!ClientsList.contains(so)) {
            ClientsList.add(so);
        }
    }
}
