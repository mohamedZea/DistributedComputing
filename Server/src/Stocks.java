import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Stocks{
    public StockType _type;
    public String _code;
    public int _amount;
    public double _unitPrice;

    public static String Serialize(Stocks obj){
        Gson gs = new Gson();
        return gs.toJson(obj);
    }

    public static Stocks Deserialize(String obj){
        Gson gs = new Gson();
        return gs.fromJson(obj,Stocks.class);
    }

}

