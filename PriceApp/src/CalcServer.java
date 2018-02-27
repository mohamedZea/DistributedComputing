import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.util.Dictionary;

public class CalcServer {

    public Integer add(int x, int y) {
        return new Integer(x + y);
    }

    public Integer sub(int x, int y) {
        return new Integer(x - y);
    }

    public Integer mul(int x, int y) {
        return new Integer(x * y);
    }

    public double getPrice(String action) {


        Dictionary<String, Double> prices ;

        prices.put()


    }

    private static final int port = 8080;

    public static void main(String[] args) {
        try {

            WebServer webServer = new WebServer(port);

            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
            PropertyHandlerMapping phm = new PropertyHandlerMapping();

            phm.addHandler("Calculator", CalcServer.class);
            xmlRpcServer.setHandlerMapping(phm);

            XmlRpcServerConfigImpl serverConfig =
                    (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            // serverConfig.setEnabledForExtensions(true);
            // serverConfig.setContentLengthOptional(false);

            webServer.start();

            System.out.println("The Calculator Server has been started...");

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }
    }
}
