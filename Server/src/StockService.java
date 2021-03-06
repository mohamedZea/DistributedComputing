import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;

public class StockService extends Thread {
    Socket client;
    StocksManager stockManager;

    BufferedReader fromClient;
    DataOutputStream toClient;

    Object lock = new Object();

    StockService(Socket client, StocksManager stockManager) {
        this.client = client;
        this.stockManager = stockManager;
    }

    @Override
    public void run() {
        String line;
        boolean verbunden = true;
        TCPServer.RegisterConnection(this);
        System.out.println("Thread started: " + this); // Display Thread-ID
        try {
            fromClient = new BufferedReader              // Datastream FROM Client
                    (new InputStreamReader(client.getInputStream()));
            toClient = new DataOutputStream(client.getOutputStream()); // TO Client


            while (verbunden) {     // repeat as long as connection exists
                line = fromClient.readLine();              // Read Request
                System.out.println("Received: " + line);
                if(line != null || !line.equals(".")) {
                    try {
                        Stocks.Deserialize(line);

                        ProcessRequest(line);
                        System.out.println(stockManager.BidsList);
                        System.out.println(stockManager.AsksList);

                    } catch (Exception e) {

                    }
                }
                if (line.equals(".")) verbunden = false;   // Break Conneciton?
            }
            fromClient.close();
            toClient.close();
            client.close(); // End
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Thread ended: " + this);
        TCPServer.UnregisterConnection(this);
    }

    void ProcessRequest(String line) throws IOException {
        Stocks sto = Stocks.Deserialize(line);
        sto._owner = client.getRemoteSocketAddress().toString();
        stockManager.RegisterStock(sto);
        System.out.println(stockManager.BidsList);
        System.out.println(stockManager.AsksList);
        //Mettre en attente le client
        toClient.writeBytes("wait" + "\n");
        //Check Updates
        if(sto._type == StockType.Ask) {

                AskForStock(sto);
        }
        else
        {
          //  BidForStock();
        }
    }

    private void BidForStock() {
        try {
            synchronized (lock) {
                wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void AskForStock(Stocks sto) {
        Optional<Stocks> tmp = null;
        synchronized (sto) {
            while (!(tmp = stockManager.FindMatch(sto)).isPresent()) {
            }
            Match(sto, tmp.get());
        }
    }

    void Match(Stocks ask, Stocks bid){
        //Prepare Message for bidder
        Stocks cpyToBid = (Stocks)ask.Clone();
        cpyToBid._owner = bid._owner;
        cpyToBid._unitPrice = bid._unitPrice;
        //Prepare Message for asker
        Stocks cpyToAsk = (Stocks)ask.Clone();
        cpyToAsk._type = StockType.Bid;
        cpyToAsk._unitPrice = bid._unitPrice;

        System.out.println("Match Found between " + cpyToBid._owner + " & " + cpyToAsk._owner);
        try {
            NotifyOwner(cpyToBid);
            SendStocks(cpyToAsk);
            stockManager.UpdateStocks(ask,bid);
            CsvModel.logToCSV(cpyToBid, cpyToAsk);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    void NotifyOwner(Stocks sto) throws IOException {
        Optional<StockService> cli = TCPServer.ClientsList.stream().filter(x -> sto._owner.equals(x.client.getRemoteSocketAddress().toString())).findFirst();
        if(cli.isPresent()) {
            StockService curr = cli.get();
            curr.SendStocks(sto);
            synchronized (curr.lock) {
                curr.lock.notify();
            }

        }
    }

    public void SendStocks(Stocks sto){
        try {

                toClient.writeBytes(Stocks.Serialize(sto) + "\n");

        } catch (IOException e) {
        System.out.println("Imposible to reach " + sto._owner);
            e.printStackTrace();
        }
    }
}