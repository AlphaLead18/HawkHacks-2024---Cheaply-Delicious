package Tests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {

        String userAgents[] = {"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36",
        };
        int rnd = (int) (Math.random() * userAgents.length);


        String googleUrl = "https://www.google.com/search?q=tomato+walmart&num=10";

        // Connect to the Google search page
        Document doc = Jsoup.connect(googleUrl).userAgent(userAgents[rnd]).get();        // Document object represents the HTML dom (Talking about "doc" here)

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
}