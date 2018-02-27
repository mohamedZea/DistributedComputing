import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class PriceServer {


    public double getPrice(String action) {
        Map<String, Double> prices = new HashMap<String, Double>();

        prices.put("Apple", 10.5);
        prices.put("Microsoft", 1000.5);

        if (prices.containsKey(action.toString())) {
;
            return prices.get(action);

        } else {
            return 0;
        }
    }

    private static final int port = 8080;

    public static void main(String[] args) {
        try {

            WebServer webServer = new WebServer(port);

            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
            PropertyHandlerMapping phm = new PropertyHandlerMapping();

            phm.addHandler("Price", PriceServer.class);
            xmlRpcServer.setHandlerMapping(phm);

            XmlRpcServerConfigImpl serverConfig =
                    (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            // serverConfig.setEnabledForExtensions(true);
            // serverConfig.setContentLengthOptional(false);

            webServer.start();

            System.out.println("The Price Server has been started...");

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }
    }
}
