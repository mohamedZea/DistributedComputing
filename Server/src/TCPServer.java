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

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TCPServer {


    public double getPrice(String code) {

        for (Stocks s : stockManager.BidsList) {
            if (s._code == code) {
                return s._unitPrice;
            }
        }
        return -1;
    }

    public static ArrayList<StockService> ClientsList = new ArrayList<StockService>();
    static StocksManager stockManager = new StocksManager();

    public static void main(String[] args) throws Exception {
        int port = 9999;
        ServerSocket listenSocket = new ServerSocket(port);
        System.out.println("Multithreaded Server starts on Port " + port);

        Thread thread = new Thread() {
            public void run() {
                try {

                    WebServer webServer = new WebServer(8080);

                    XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
                    PropertyHandlerMapping phm = new PropertyHandlerMapping();

                    phm.addHandler("Price", TCPServer.class);
                    xmlRpcServer.setHandlerMapping(phm);

                    XmlRpcServerConfigImpl serverConfig =
                            (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
                    // serverConfig.setEnabledForExtensions(true);
                    // serverConfig.setContentLengthOptional(false);

                    webServer.start();

                    System.out.println("The Price Server has been started...");

                } catch (Exception exception)

                {
                    System.err.println("JavaServer: " + exception);
                }
            }
        };

        thread.start();

        while (true)

        {
            Socket client = listenSocket.accept();
            System.out.println("Connection with: " +     // Output connection
                    client.getRemoteSocketAddress());   // (Client) address
            StockService s = new StockService(client, stockManager);
            RegisterConnection(s);
            s.start();

        }

    }

    static void RegisterConnection(StockService so) {
        if (!ClientsList.contains(so)) {
            ClientsList.add(so);
        }
    }
}
