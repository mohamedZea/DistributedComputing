import java.rmi.*;
import java.util.*;

public interface PriceInterface extends Remote {
    public double getPrice(String action) throws RemoteException;
}