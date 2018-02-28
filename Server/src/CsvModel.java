import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvModel {

    static void logToCSV(Stocks bid, Stocks ask) throws IOException {

        FileWriter fw = null;
        try {
            fw = new FileWriter("log.csv", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = getStringWriter(bid, ask);

       fw.write(sb.toString());
        fw.close();
    }

    private static StringBuilder getStringWriter(Stocks bid, Stocks ask) {
        StringBuilder sb  = new StringBuilder();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        sb.append(date);
        sb.append(';');
        sb.append(ask._code);
        sb.append(';');
        sb.append(ask._amount);
        sb.append(';');
        sb.append(ask._unitPrice);
        sb.append(';');
        sb.append(ask._owner);
        sb.append(';');
        sb.append(bid._owner);
        sb.append('\n');
        return sb;
    }
}
