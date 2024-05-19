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

public class LocationParse {
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

        try {
            connection = new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String s = content;
        String plainText= Jsoup.parse(s).text();
        System.out.println(plainText);
        int start;
        if(plainText.contains("Address: ")){
            start = plainText.indexOf("Address: ");
            start += 9;
        }
        else{
            start = plainText.indexOf(" A Loblaws ");
            start += 26;
        }
        int end = start;
        int count = 0;
        while (count != 3) {
            end++;
            if (plainText.charAt(end) == ' ') {
                count++;
            }
        }
        String currentString = "";
        for (int i = start; i <= end; i++) {
            currentString += Character.toString(plainText.charAt(i));
        }
        System.out.println(currentString);

        //-----

        uri = null;
        googleUrl = "https://www.google.com/search?q=";
        System.out.println("Input query:");
        searchQuery = createQuery(in.nextLine());
        query = googleUrl + searchQuery;

        uri = new URI(query);

        content = null;
        connection = null;

        url = uri.toASCIIString();

        try {
            connection = new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        s = content;
        plainText= Jsoup.parse(s).text();
        System.out.println(plainText);
        start = plainText.indexOf(" min (");
        start += 6;
        end = start;
        while (plainText.charAt(end) != 'm') {
            end++;
        }
        currentString = "";
        for (int i = start; i <= end; i++) {
            currentString += Character.toString(plainText.charAt(i));
        }
        System.out.println(currentString);
    }
        static String createQuery(String query) {
        query = query.replaceAll(" ", "+");
        query += "&num=10";
        return query;
    }
}