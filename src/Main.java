import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/// The Main class serves as the entry point for a stock price retrieval and display application.
/// It interacts with the Yahoo Finance webpage to fetch stock information for a user-specified ticker symbol.
/// The primary responsibilities of the class include:
/// - Prompting the user for a stock ticker symbol.
/// - Fetching the associated stock data (name and price) by scraping the Yahoo Finance website.
/// - Displaying the stock information in a structured format.
/// The class also calls a helper method to parse and format the stock price details.
///
/// @throws IOException if an input or output exception occurs while accessing external resources.
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
