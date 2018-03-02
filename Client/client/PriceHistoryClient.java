package client;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import static javafx.application.Application.launch;


public class PriceHistoryClient {

    public static Double[] listPrice;

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
        config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        boolean isPossible = true;

        while (true) {

            //We need some information to transmit to the server
            System.out.print("Enter a stock code (example : AAPL) : ");
            String action = user.input();// code of the stock

            Date beginDate = new Date(); // Date og the beginning of the history
            Date endDate = new Date();// Date og the end of the history


            System.out.print("Enter the beginning day history (Today :" + beginDate.getDate() + " ) : ");
            beginDate.setDate(Integer.parseInt(user.input()));
            System.out.print("Enter the beginning month history (Today :" + beginDate.getMonth() + " ) : ");
            beginDate.setMonth(Integer.parseInt(user.input()) - 1);
            System.out.println("");
            System.out.print("Enter the beginning hour history (Today :" + beginDate.getHours() + " ) : ");
            beginDate.setHours(Integer.parseInt(user.input()));
            System.out.print("Enter the beginning minute history (Today :" + beginDate.getMinutes() + " ) : ");
            beginDate.setMinutes(Integer.parseInt(user.input()));
            beginDate.setSeconds(0);

            System.out.println("");
            System.out.println("");

            System.out.print("Enter the end day history (Today :" + endDate.getDate() + " ) : ");
            endDate.setDate(Integer.parseInt(user.input()));
            System.out.print("Enter the end month history (Today :" + endDate.getMonth() + " ) : ");
            endDate.setMonth(Integer.parseInt(user.input()) - 1);
            System.out.println("");
            System.out.print("Enter the end hour history (Today :" + endDate.getHours() + " ) : ");
            endDate.setHours(Integer.parseInt(user.input()));
            System.out.print("Enter the end minute history (Today :" + endDate.getMinutes() + " ) : ");
            endDate.setMinutes(Integer.parseInt(user.input()));
            endDate.setSeconds(0);

            Object[] params = new Object[]{action, beginDate, endDate};

            Object[] result = (Object[]) client.execute("Price.getHistory", params); // We call the procedure with params and we handle the result
            listPrice = new Double[result.length];

            if (result.length == 0) {
                System.out.println("No price found for this stock during this period. Try Again."); // if we don't have result
            } else {

                for (int i = 0; i < result.length; i++) {
                    listPrice[i] = (Double) result[i];
                    System.out.print(listPrice[i] + " ; ");
                }
                if (isPossible) {
                    isPossible = false;
                    new DisplayGraphics().launch(DisplayGraphics.class); // show a graph but only once (JAVAFX launch, doesn't permit to launch more than one time and I didn't found a solution)
                }
            }
        }
    }
}

