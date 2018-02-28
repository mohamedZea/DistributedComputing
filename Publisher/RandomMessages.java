import java.util.Random;

public class RandomMessages {

    static String Generate()
    {
        Random rand = new Random();
        int newsType = rand.nextInt(NewsType.values().length);
        int codType = rand.nextInt(StockCode.values().length);
        return NewsType.values()[newsType].name() + " news for " + StockCode.values()[codType].name();
    }
}
