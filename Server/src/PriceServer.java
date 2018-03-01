import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.util.ArrayList;
import java.util.Date;

public class PriceServer extends Thread implements Runnable {

    static StocksManager stockManager = TCPServer.stockManager;

    public void run() {
        try {

            WebServer webServer = new WebServer(8080);

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

        } catch (Exception exception)

        {
            System.err.println("JavaServer: " + exception);
        }
    }

    public double getPrice(String code) {
        ArrayList<Stocks> list = new ArrayList<Stocks>();

        stockManager.TransactionList.forEach(x -> {
            if (x.stocks._code.equals(code)) {
                list.add(x.stocks);
            }
        });

        if (list.isEmpty()) {
            return -1;
        } else {
            return list.get(list.size() - 1)._unitPrice;
        }
    }

    public Double[] getHistory(String code, Date beginDate, Date endDate) {

        ArrayList<Stocks> list = new ArrayList<Stocks>();

        stockManager.TransactionList.forEach(x -> {
            if (x.stocks._code.equals(code) && x.date.after(beginDate)  && x.date.before(endDate)) {
                list.add(x.stocks);
            }
        });
        System.out.println(list.size());
        Double[] tab = new Double[list.size()];

        for (int i = 0; i < list.size(); i++) {
            tab[i] = list.get(i)._unitPrice;
        }

        return tab;
    }
}
