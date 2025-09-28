import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/// The entry point of the program. This method retrieves the stock name and price
/// for a given stock ticker symbol by fetching data from Yahoo Finance.
/// The stock ticker symbol can be provided as a command-line argument.
/// If not provided, the user will be prompted to input it. The stock's
/// information is then processed and displayed.
///
/// @param args command-line arguments. args[0] may contain the stock ticker symbol
///             to be used for fetching the stock's information.
/// @throws IOException if there is an error in reading input or connecting to the
///                     website to retrieve stock data.
void main(String[] args) throws IOException {
    final String version = "1.1.0";

    // 1) Handle flags first
    if (args.length > 0 && ("--version".equals(args[0]) || "-v".equals(args[0]))) {
        System.out.println("StockPrice version " + version);
        System.exit(0);
    }
    if (args.length > 0 && ("--help".equals(args[0]) || "-h".equals(args[0]))) {
        printHelp(version);
        System.exit(0);
    }

    String firstArg = (args.length > 0 && !args[0].isBlank()) ? args[0] : null;

    IO.println("Welcome to Stock Price " + version + "!");

    String stockSymbol;
    if (firstArg == null) {
        stockSymbol = IO.readln("Please enter a stock ticker symbol (e.g. AAPL): ");
    } else {
        stockSymbol = firstArg;
    }

    if (stockSymbol == null || stockSymbol.isBlank()) {
        System.err.println("No stock symbol provided.");
        System.exit(1);
    }

    String stockName = null;
    String stockPrice = null;
    try {
        final Document doc = Jsoup.connect("https://finance.yahoo.com/quote/" + stockSymbol).get();

        stockName = Objects.requireNonNull(doc.selectFirst("h1.yf-4vbjci")).text();
        stockPrice = Objects.requireNonNull(doc.selectFirst(".price")).text();
    } catch (HttpStatusException e) {
        if(e.getStatusCode()  == 404) {
            System.err.println("Cannot find the stock ticker symbol: " + stockSymbol);
            System.exit(1);
        }
        System.err.println("Error: " + e.getStatusCode() + ": " + e.getMessage());
        System.exit(1);
    }
    IO.println();
    IO.println("Stock Name: " + stockName);
    IO.print(formatAndDisplayStockPrice(stockPrice));
}

/// Formats and displays stock price information extracted from a raw string input.
/// The method parses the given stock price data, organizes it into a readable layout,
/// and returns the formatted output as a string. If the input is null or empty,
/// a message indicating the absence of stock price data is printed and the method returns null.
///
/// @param stockPrice the raw string containing stock price information. Expected to include
///                   various parts of stock data separated by whitespace.
/// @return a formatted string containing structured stock price information. Returns null if the
///         input is null or empty, or if an unexpected format causes an exception.
public static String formatAndDisplayStockPrice(String stockPrice) {
    if (stockPrice == null || stockPrice.isEmpty()) {
        IO.println("No stock price data available.");

        return null;
    }
    final String[] parts = stockPrice.split("\\s+");
    final StringBuilder formattedOutput = new StringBuilder();

    try {
        formattedOutput.append(String.format("""
            
            ---------- Stock Price Information ----------
            Stock Price (At close) : %s
            Change                 : %s (%s%%)
            Closing Time           : %s %s %s %s
            After Hours Price      : %s
            Change (After hours)   : %s (%s)
            After Hours Time       : %s %s %s
            
            ----------- Additional Information -----------
            """, parts[2], parts[3], parts[4], parts[6], parts[7], parts[8],
                parts[9], parts[10], parts[11], parts[12], parts[14], parts[15], parts[16]));

        for (int i = 17; i < parts.length; i++) {
            formattedOutput.append(String.format("Metric %d: %s%n", (i - 16), parts[i]));
        }

    } catch (ArrayIndexOutOfBoundsException e) {
        System.err.println("Unexpected format in stock price data. Raw data: " + stockPrice);
    }
    return formattedOutput.toString();
}

private static void printHelp(String version) {
    IO.println("StockPrice version " + version);
    IO.println("Usage: run.bat <stock-symbol> or run.sh <stock-symbol>");
    IO.println("you can also run the Main.class directly with: java Main.java");
    IO.println("Options:");
    IO.println("  --version   Show version");
    IO.println("  --help      Show help");
}
