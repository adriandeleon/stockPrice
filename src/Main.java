import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

void main() throws IOException {

    IO.println("Welcome to Stock Price!");
    final String stockSymbol =  IO.readln("Please enter a stock ticker symbol (e.g. AAPL): ");

    final Document doc = Jsoup.connect("https://finance.yahoo.com/quote/" + stockSymbol).get();
    final String stockName = Objects.requireNonNull(doc.selectFirst("h1.yf-4vbjci")).text();
    final String stockPrice = Objects.requireNonNull(doc.selectFirst(".price")).text();

    IO.println();
    IO.println("Stock Name: " + stockName);
    IO.print(formatAndDisplayStockPrice(stockPrice));
}

public static String formatAndDisplayStockPrice(String stockPrice) {

    if (stockPrice == null || stockPrice.isEmpty()) {
        IO.println("No stock price data available.");

        return null;
    }

    String[] parts = stockPrice.split("\\s+");
    StringBuilder formattedOutput = new StringBuilder();

    try {
        formattedOutput.append(String.format("""
            
            ---------- Stock Price Information ----------
            Stock Price (At close): %s
            Change: %s (%s%%)
            Closing Time: %s %s %s %s
            After Hours Price: %s
            Change (After hours): %s (%s)
            After Hours Time: %s %s %s
            
            ----------- Additional Information -----------
            """, parts[2], parts[3], parts[4], parts[6], parts[7], parts[8],
                parts[9], parts[10], parts[11], parts[12], parts[14], parts[15], parts[16]));

        for (int i = 17; i < parts.length; i++) {
            formattedOutput.append(String.format("Metric %d: %s%n", (i - 16), parts[i]));
        }

    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Unexpected format in stock price data. Raw data: " + stockPrice);
    }
    return formattedOutput.toString();
}
