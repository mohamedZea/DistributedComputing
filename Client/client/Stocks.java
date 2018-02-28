package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Random;

public class Stocks implements Cloneable{
    public StockType _type;
    public String _code;
    public int _amount;
    public double _unitPrice;
    public String _owner;

    public static String Serialize(Stocks obj){
        Gson gs = new Gson();
        return gs.toJson(obj);
    }

    public static Stocks Deserialize(String obj){
        Gson gs = new Gson();
        return gs.fromJson(obj,Stocks.class);
    }

    public Object Clone(){
        Object o = null;
        try {
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return o;
    }
    public void createRandomStock(){
        Random rand = new Random();
        int valueRand = rand.nextInt(2);
        if(valueRand == 0)
            this._type = StockType.Ask;
        else
            this._type = StockType.Bid;
        valueRand = rand.nextInt(3);
        switch (valueRand){
            case 0: this._code = StockCode.AAPL.name();
                break;
            case 1: this._code = StockCode.IBM.name();
                break;
            case 2: this._code = StockCode.MSFT.name();
                break;
            default: this._code = StockCode.ORCL.name();
                break;
        }
        valueRand = rand.nextInt(9999);
        this._unitPrice = 1000;
        this._amount = valueRand;

    }
}
