package client;

import java.net.URL;

import client.UserInterface;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;


public class PriceClient {
    /**
     * Main program of PriceHistoryClient : It will create the xmlRpc Client and call the procedure with parameters
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        UserInterface user = new UserInterface();

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        // We bound the configuration
        config.setServerURL(new URL("http://:8080/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        while (true) {
            System.out.print("Enter a stock code (example : AAPL) : ");
            String action = user.input(); // code of the stock

            Object[] params = new Object[]{action};

            Double result = (Double) client.execute("Price.getPrice", params);// We call the procedure with params and we cast the result into a double

            if (result == -1) {
                System.out.println("Action doesn't exist");
            } else {
                System.out.println("Price = " + result); // Display the result
            }

        }
    }
}

