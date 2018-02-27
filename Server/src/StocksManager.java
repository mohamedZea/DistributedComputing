import java.util.ArrayList;
import java.util.Optional;

public class StocksManager {

    static ArrayList<Stocks> AsksList = new ArrayList<Stocks>();
    static ArrayList<Stocks> BidsList = new ArrayList<Stocks>();

    public void RegisterStock(Stocks sto)
    {
        if(sto._type == StockType.Bid){
            BidsList.add(sto);

        }else{
            AsksList.add(sto);
        }

        MergeStocks(sto);
    }

    private void MergeStocks(Stocks sto)
    {
        ArrayList<Stocks> currentList;
        if(sto._type == StockType.Bid){
            currentList = BidsList;
        }else{
            currentList = AsksList;
        }

        Optional<Stocks> target = currentList.stream().filter(x -> x._owner == sto._owner && x._code == sto._code && sto._unitPrice == x._unitPrice).findFirst();
        if(target.isPresent()){
            target.get()._amount += sto._amount;
        }

    }

    public Optional<Stocks> FindMatch(Stocks ask) {
        return BidsList.stream().filter(x -> x._code.equals(ask._code) && ask._unitPrice >= x._unitPrice && x._amount >= ask._amount && x._type != ask._type).findFirst();
    }

    public static void UpdateStocks(Stocks ask, Stocks bid) {
        if(bid._amount > ask._amount){
            bid._amount -= ask._amount;
        }
        else
        {
            BidsList.remove(bid);
        }
    }
}
