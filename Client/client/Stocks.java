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
        int valueRand = rand.nextInt(StockType.values().length);
        // random stocktype
            this._type = StockType.values()[valueRand];
        // random stockcode
            valueRand = rand.nextInt(StockCode.values().length);
            this._code = StockCode.values()[valueRand].toString();
        // unitprice
            valueRand = rand.nextInt(12-10)+10;
            this._unitPrice = valueRand;
        // random amount number
            valueRand = rand.nextInt(9999);
            this._amount = valueRand;
    }
    public void createMOMStock(String stockCode, boolean isAsking){
        Random rand = new Random();
        int valueRand;
        // random stocktype
        if (isAsking)
            this._type = StockType.Ask;
        else
            this._type = StockType.Bid;
        // random stockcode
        this._code = stockCode;
        // unitprice
        valueRand = rand.nextInt(12-10)+10;
        this._unitPrice = valueRand;
        // random amount number
        valueRand = rand.nextInt(9999);
        this._amount = valueRand;
    }

}
