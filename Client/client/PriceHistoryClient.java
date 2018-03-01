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

    public static void main(String[] args) throws Exception {


        UserInterface user = new UserInterface();

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

        config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);


        while (true) {
            System.out.print("Enter a stock code (example : AAPL) : ");
            String action = user.input();

            Date beginDate = new Date();
            Date endDate = new Date();


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

            Object[] result = (Object[]) client.execute("Price.getHistory", params);
            listPrice = new Double[result.length];

            if (result.length == 0) {
                System.out.print("No price found for this stock during this period. Try Again.");
            } else {
                for (int i = 0; i < result.length; i++) {
                    listPrice[i] = (Double) result[i];
                }

                new DisplayGraphics().launch(DisplayGraphics.class);
            }
        }
    }
}

