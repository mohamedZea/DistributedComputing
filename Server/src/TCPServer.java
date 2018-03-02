// 22. 10. 10

/**
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class TCPServer {

    static final int MAX_CONNECTIONS = 10;

    final static ArrayList<StockService> ClientsList = new ArrayList<StockService>();
    final static StocksManager stockManager = new StocksManager();

    public static void main(String[] args) throws Exception {
        int port = 9999;

        ServerSocket listenSocket = new ServerSocket(port);
        System.out.println("Multithreaded Server starts on Port " + port);
        Thread thread = new Thread() {
            public void run() {
                try {
                    PriceServer priceServer = new PriceServer();
                    priceServer.run();
                } catch (Exception exception)

                {
                    System.err.println("JavaServer: " + exception);
                }
            }
        };
        thread.start();

        while (true)
        {
            int num = ClientsList.size();
            if(num < MAX_CONNECTIONS) {
                Socket client = listenSocket.accept();
                System.out.println("Connection with: " +     // Output connection
                        client.getRemoteSocketAddress());   // (Client) address
                StockService s = new StockService(client, stockManager);
                s.start();
            }
        }

    }

    static void RegisterConnection(StockService so) {
        if (!ClientsList.contains(so)) {
            ClientsList.add(so);
        }
    }

    static void UnregisterConnection(StockService so) {
        ClientsList.remove(so);
    }
}
