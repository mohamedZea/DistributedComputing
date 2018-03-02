package client;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.net.Socket;
import java.util.Random;

public class ImprovedTraders {
    //static boolean waiting;
    static String line;
    static Socket socket;
    static BufferedReader fromServer;
    static DataOutputStream toServer;
    static UserInterface user = new UserInterface();
    static int StrategyType = 0;
    static String IPAddress = "172.17.1.243";
    static int PortServer = 9999;

    public static void main(String []args) throws JMSException {
        // we make a random selection between a cyclic strategy and an acyclic strategy
        selectStrategy();
        System.out.println("Listener launched");
        try {
            // creating the socket and the input/output streams
            socket = new Socket(IPAddress, PortServer);
            toServer = new DataOutputStream(socket.getOutputStream());
            InputStreamReader dataInputStream = new InputStreamReader(socket.getInputStream());
            fromServer = new BufferedReader(dataInputStream);
        }catch(Exception e){
            System.err.println(e.toString());
        }

        // defining all the data used to communicate with the publisher
        String user = env("ACTIVEMQ_USER", "admin");
        String password = env("ACTIVEMQ_PASSWORD", "password");
        String host = env("ACTIVEMQ_HOST", IPAddress);
        int port = Integer.parseInt(env("ACTIVEMQ_PORT", "61616"));
        String destination = arg(args, 0, "event");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
        Connection connection = factory.createConnection(user, password);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = new ActiveMQTopic(destination);

        // creating a listener/publisher session
        MessageConsumer consumer = session.createConsumer(dest);
        System.out.println("Waiting for messages...");

        // our big fat loop to keep it running
        while(true) {
            Message msg = consumer.receive();
            if( msg instanceof  TextMessage ) {
                String body = ((TextMessage) msg).getText();
                System.out.println(body);
                // if the server want to close the session
                if( "SHUTDOWN".equals(body)) {
                    System.out.println("Listener shut down");
                    break;
                }
                else {
                    // getting a random request based on the publisher's message
                    Stocks stock = getStocks(body);
                    try {
                        // sending it to the broker
                        sendRequest(stock);
                        // handling the broker response
                        while(!fromServer.readLine().contains(stock._type.toString())){
                            if(receiveResponse())
                                break;
                            else
                                System.out.println("Listener waiting");
                        }
                        System.out.println("Listener restarted");
                    }catch(Exception e)
                    {
                        System.err.println(e.toString());
                    }

                }

            } else {
                System.out.println("Unexpected message type: "+msg.getClass());
            }
        }
        // closing the connection with the publisher
        connection.close();
    }

    // we select a random strategy
    private static void selectStrategy(){
        Random rand = new Random();
        //Decide of the strategy cyclic or not
        StrategyType = rand.nextInt(2) + 0;
        if(StrategyType ==1){
            System.out.println("Acyclic");
        }else{
            System.out.println("Cyclic");
        }
    }

    // parsing the publisher message and creating a random request based on the message data and the strategy chose
    private static Stocks getStocks(String body) {
        String[] message = body.split(" ");
        Stocks stock = new Stocks();
        System.out.println("");
        if (message[0].equals("Good")) {
            if(StrategyType == 1)
                stock.createMOMStock(message[3], false);
            else
                stock.createMOMStock(message[3], true);
        }else{
            if(StrategyType == 1)
                stock.createMOMStock(message[3], true);
            else
                stock.createMOMStock(message[3], false);
        }
        return stock;
    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if( rc== null )
            return defaultValue;
        return rc;
    }
    // method to send request to the server
    private static void sendRequest(Stocks sto) throws IOException {
        // we display it in the terminal
        System.out.println("--->Trader request: " + Stocks.Serialize(sto));
        // we send it to the server
        toServer.writeBytes(Stocks.Serialize(sto)+ '\n');
    }
    // function to handle server's responses
    private static boolean receiveResponse() throws IOException {
        boolean test = false;
        // we get the server response
        String serverResponse = fromServer.readLine();
        // we display it in the terminal
        user.output("--->Server answers: " + serverResponse + '\n');
        // if the response is actually a match
        if (!serverResponse.equals("wait")){
            try{
                Stocks.Deserialize(serverResponse);
                user.output("---> MATCH <---");
                test = true;
            }catch(Exception e){
                System.err.println("Exception: " + e.toString());
                e.printStackTrace();
            }
        }
        return test;
    }

    private static String arg(String []args, int index, String defaultValue) {
        if( index < args.length )
            return args[index];
        else
            return defaultValue;
    }
}