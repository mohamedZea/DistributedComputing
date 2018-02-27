import java.util.ArrayList;
import java.util.Optional;

public class StocksManager {

    public ArrayList<Stocks> AsksList = new ArrayList<Stocks>();
    public ArrayList<Stocks> BidsList = new ArrayList<Stocks>();

    public void RegisterStock(Stocks sto)
    {
        if(MergeStocks(sto)){
            return;
        }

        if(sto._type == StockType.Bid){
            BidsList.add(sto);

        }else{
            AsksList.add(sto);
        }
    }

    private boolean MergeStocks(Stocks sto)
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
            return true;
        }else{
            return false;
        }

    }

    public Optional<Stocks> FindMatch(Stocks ask) {
        return BidsList.stream().filter(x -> x._code.equals(ask._code) && ask._unitPrice >= x._unitPrice && x._amount >= ask._amount).findFirst();
    }

    public void UpdateStocks(Stocks ask, Stocks bid) {
        if(bid._amount > ask._amount){
            bid._amount -= ask._amount;
        }
        else
        {
            BidsList.remove(bid);
        }
        AsksList.remove(ask);
    }
}
