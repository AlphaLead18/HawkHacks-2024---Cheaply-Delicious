package Tests;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GroceryParse {
    public static void main(String[]args) throws IOException, URISyntaxException {
        URI uri = null;
        String googleUrl = "https://www.google.com/search?q=";
        Scanner in = new Scanner(System.in);
        System.out.println("Input query:");
        String searchQuery = createQuery(in.nextLine());
        String query = googleUrl + searchQuery;

        uri = new URI(query);

        String content = null;
        URLConnection connection = null;

        String url = uri.toASCIIString();

        String userAgents[] = {"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36",
        };
        int rnd = (int) (Math.random() * userAgents.length);

        Document doc = Jsoup.connect(url).userAgent(userAgents[rnd]).get();        // Document object represents the HTML dom (Talking about "doc" here)

        Elements results = doc.select("div.g");

        int c = 0;
        for (Element result : results) {
            // Extract the title and link of the result
            String title = result.select("h3").text();
            String link = result.select(".yuRUbf > a").attr("href");
            String snippet = result.select(".VwiC3b").text();
            System.out.println("Title: " + title);
            System.out.println("Link: " + link);
            System.out.println("Snippet: " + snippet);
            System.out.println("Position: " + (c + 1));
            System.out.println("\n");
            c++;
        }
    }
    private static String createQuery(String query) {
        query = query.replaceAll(" ", "+");
        query += "&num=10";
        return query;
    }
}