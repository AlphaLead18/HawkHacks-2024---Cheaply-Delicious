package HawkHacks.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RestController
public class StringController {
    public String full = new String();
    @CrossOrigin(origins = "http://localhost:5500")
    @GetMapping("/getString")
    public Map<String, String> getString() {
        String link = "https://example.com"; // This is the string variable you want to send to the frontend
        return Map.of("link", full);
    }

    @CrossOrigin(origins = "http://localhost:5500")
    @PostMapping("/submit")
    @GetMapping("/getString")
    public String handleStringSubmit(@RequestBody Map<String, String> requestBody) {
        //System.out.println("Hello, world!");
        String link = requestBody.get("link");
        System.out.println("Received link: " + link);
        // Process the string variable as needed
        String content = null;
        URLConnection connection = null;
        String url = link;
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
        //Isolate ingredients and convert to list
        int start = s.indexOf("recipeIngredient");
        while (s.charAt(start) != ('[')) {
            start++;
        }
        while (s.charAt(start) != ('\"')) {
            start++;
        }
        int end = start;
        while (s.charAt(end) != (']')) {
            end++;
        }
        s = s.substring(start + 1, end - 1);
        s = s.replace("\n", "").replace("\r", "");
        s = s.replace("&amp;", "and");
        s = s.replace("each:", "");

        System.out.println(s);
        String[] ar = s.split("\",\"");

        Map<String, String> ingrList = new HashMap<String, String>();
        String[] UNITS = {"ML ", "ML.", "L ", "L. ", "MILLILITER ", "MILLILITERS", "MILLILITRE ", "MILLILITRES ", "LITER ",
                "LITERS ", "LITRE ", "LITRES ", "TSP ", "TBSP ", "TSP. ", "TBSP. ", "TEASPOON ", "TABLESPOON ", "TEASPOONS ",
                "TABLESPOONS ", "CUP ", "CUPS ", "G ", "GRAMS ", "GRAM ", "MG ", "MG. ", "MILLIGRAMS ", "MILLIGRAM ",
                "FLUID OUNCE ", "FLUID OUNCES ", "OUNCE ", "OUNCES ", "FL. OZ. ", "FL OZ ", "OZ. ", "OZ ", "CUP ", "C ", "C. ",
                "PINT ", "PINTS ", "PT ", "PT. ", "QUART ", "QUARTS ", "QT. ", "QT ", "GALLONS ", "GALLON ", "GAL. ", "GAL",
                "POUND ", "POUNDS ", "LBS. ", "LB. ", "LBS ", "LB", "KILOGRAM ", "KILOGRAMS ", "KG. ", "KG ", "DASH ", "SMALL ",
                "LARGE ", "DOZEN ", "FEW ", "SEVERAL ", "DOLLOP ", "DOLLOPS ", "PINCH ", "PINCHES ", "SMIDGEN ", "SMIDGENS ",
                "YARD ", "YARDS ", "INCH ", "INCHES ", "IN. ", "IN ", "CENTIMETERS ", "CENTIMETER", "CENTIMETRES ",
                "CENTIMETRE ", "CM. ", "CM ", "METER ", "METRE", "METERS ", "METRES ", "m ", "m. ", "MILLIMETERS ",
                "MILLIMETRES ", "MILLIMETER ", "MILLIMETRE ", "MM ", "MM. ", "GILL ", "GILLS ", "DECILITER ", "DECILITRE ",
                "DECILITERS ", "DECILITRES ", "DL. ", "DL ", "OF "};
        for (int i = 0; i < ar.length; i++) {
			/*if(ar[i].contains(",")) {
				ar[i] = ar[i].substring(0, ar[i].indexOf(","));
			}*/
            if (ar[i].contains("optional")) {
                ar[i] = "";
            }
            if (ar[i].contains("(")) {
                while (ar[i].contains("(")) {
                    ar[i] = ar[i].substring(0, ar[i].indexOf("(")) + ar[i].substring(ar[i].indexOf(")") + 1, ar[i].length());
                }
            }
            System.out.println(ar[i]);
            for (int j = 0; j < UNITS.length; j++) {
                if ((ar[i].toUpperCase()).contains(UNITS[j])) {
                    String name = ar[i].substring((ar[i].toUpperCase()).indexOf(UNITS[j]) + UNITS[j].length(), ar[i].length());
                    String unit = ar[i].substring(0, (ar[i].toUpperCase()).indexOf(UNITS[j]) + UNITS[j].length());
                    ingrList.put(name, unit);
                    //System.out.println(name);
                    //System.out.println(unit);
                }
            }
        }
        for (String ingr : ingrList.keySet()) {
            System.out.println(ingr + ": " + ingrList.get(ingr));
        }

        System.out.println("Received link: " + link);
        for(int i=0;i<ar.length;i++){
            full += ar[i];
            full += ", ";
        }
        // Return a response if necessary
        //return Map.of("status", "success", "receivedLink", link);
        return full;
    }
}
