import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }

}

