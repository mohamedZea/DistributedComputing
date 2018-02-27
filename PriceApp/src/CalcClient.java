import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;


public class CalcClient {
    public static void main(String[] args) throws Exception {


        UserInterface user = new UserInterface();

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

        config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        while (true) {
            String action  = user.input();

            Object[] params = new Object[]{action};
            System.out.println("About to get results...(params[0] = " + params[0]
                    + ").");

            Double result = (Double) client.execute("Calculator.getPrice", params);
            System.out.println("Price = " + result);
        }
    }
}

