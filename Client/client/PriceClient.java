package client;

import java.net.URL;

import client.UserInterface;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;


public class PriceClient {
    public static void main(String[] args) throws Exception {


        UserInterface user = new UserInterface();

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

        config.setServerURL(new URL("http://:8080/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        while (true) {
            String action = user.input();

            Object[] params = new Object[]{action};

            Double result = (Double) client.execute("Price.getPrice", params);

            if (result == -1) {
                System.out.println("Action doesn't exist");
            } else {
                System.out.println("Price = " + result);
            }

        }
    }
}

