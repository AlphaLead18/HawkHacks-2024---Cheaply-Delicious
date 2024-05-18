package Tests;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ListParse {
	public static void main(String[]args) throws IOException {
/*
		URL oracle = new URL("https://sallysbakingaddiction.com/apple-pie-recipe/");
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

		String inputLine;
		String s = "";

		while ((inputLine = in.readLine()) != null) {
			s += inputLine;
		}
*/
		String content = null;
		URLConnection connection = null;
		Scanner in = new Scanner(System.in);
		System.out.println("Input url");
		String url = in.nextLine();
		in.close();
		try {
			connection =  new URL(url).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
			scanner.close();
		}catch ( Exception ex ) {
			ex.printStackTrace();
		}
		String s = content;
		//String s = " \"prepTime\":\"PT15M\",\"cookTime\":\"PT45M\",\"totalTime\":\"PT60M\",\"recipeIngredient\":[\"6  golden delicious apples, peeled and chopped ((other varieties can be used, can also be sliced))\",\"2 Tbsp granulated sugar\",\"1 3/4 tsp ground cinnamon, (divided)\",\"1 1/2 tsp lemon juice\",\"1 cup light brown sugar\",\"3/4 cup old fashioned oats\",\"3/4 cup all-purpose flour\",\"1/2 cup cold unsalted butter, diced into small cubes\",\"pinch of kosher salt\"],\"recipeInstructions\":[{\"@type\":\"HowToStep\",\"text\":\"Preheat oven to 350 F degrees. &nbsp;Butter an 8x8 baking dish, or spray with non-stick cooking spray. &nbsp;Set aside.\",\"name\":\"Preheat oven to 350 F degrees. &nbsp;Butter an 8x8 baking dish, or spray with non-stick cooking spray. &nbsp;Set aside.\",\"url\":\"https://www.thechunkychef.com/old-fashioned-easy-apple-crisp/#wprm-recipe-11755-step-0-0\"} ]\"";
		//Isolate ingredients and convert to list
		int start = s.indexOf("recipeIngredient");
		while (s.charAt(start) != ('[')) {
			start++;
		}
		while (s.charAt(start) != ('\"')) {
			start ++;
		}
		int end = start;
		while (s.charAt(end) != (']')) {
			end ++;
		}
		s = s.substring(start+1, end-1);
		s = s.replace("\n", "").replace("\r", "");
		s = s.replace("&amp;","and");

		System.out.println(s);
		String [] ar = s.split("\",\"");

		Map<String, String> ingrList = new HashMap<String,String>();
		String [] UNITS = {"ML ","ML.","L ","L. ","MILLILITER ","MILLILITERS","MILLILITRE ","MILLILITRES ","LITER ",
				"LITERS ","LITRE ","LITRES ","TSP ","TBSP ","TSP. ","TBSP. ","TEASPOON ","TABLESPOON ","TEASPOONS ",
				"TABLESPOONS ","CUP ","CUPS ","G ","GRAMS ","GRAM ", "MG ","MG. ","MILLIGRAMS ","MILLIGRAM ",
				"FLUID OUNCE ","FLUID OUNCES ","OUNCE ","OUNCES ","FL. OZ. ", "FL OZ ","OZ. ","OZ ","CUP ","C ","C. ",
				"PINT ","PINTS ","PT ","PT. ","QUART ","QUARTS ","QT. ","QT ", "GALLONS ","GALLON ","GAL. ","GAL",
				"POUND ","POUNDS ","LBS. ","LB. ","LBS ","LB","KILOGRAM ", "KILOGRAMS ","KG. ","KG ","DASH ","SMALL ",
				"LARGE ","DOZEN ","FEW ","SEVERAL ","DOLLOP ","DOLLOPS ","PINCH ","PINCHES ","SMIDGEN ","SMIDGENS ",
				"YARD ","YARDS ","INCH ","INCHES ","IN. ","IN ","CENTIMETERS ","CENTIMETER","CENTIMETRES ",
				"CENTIMETRE ","CM. ","CM ","METER ","METRE","METERS ","METRES ","m ","m. ","MILLIMETERS ",
				"MILLIMETRES ","MILLIMETER ","MILLIMETRE ","MM ","MM. ","GILL ","GILLS ","DECILITER ","DECILITRE ",
				"DECILITERS ","DECILITRES ","DL. ","DL ","OF "};
		for(int i=0;i<ar.length;i++) {
			/*if(ar[i].contains(",")) {
				ar[i] = ar[i].substring(0, ar[i].indexOf(","));
			}
			/*if(ar[i].contains("(")){
				ar[i] = ar[i].substring(0,ar[i].indexOf("(")) + ar[i].substring(ar[i].indexOf(")")+1,ar[i].length());
			}*/
			System.out.println(ar[i]);
			for(int j=0;j< UNITS.length;j++){
				if((ar[i].toUpperCase()).contains(UNITS[j])){
					String name = ar[i].substring((ar[i].toUpperCase()).indexOf(UNITS[j]) + UNITS[j].length(), ar[i].length());
					String unit = ar[i].substring(0,(ar[i].toUpperCase()).indexOf(UNITS[j]) + UNITS[j].length());
					ingrList.put(name, unit);
					//System.out.println(name);
					//System.out.println(unit);
				}
			}
		}
		for(String ingr :  ingrList.keySet()) {
			//System.out.println(ingr + ": " + ingrList.get(ingr));
		}
	}
	static boolean hasDigit(String input) {
		return input.matches(".*\\d.*");
	}
	class Data{
		private int amount;
		private String unit;

		public Data(int amount, String unit) {
			this.amount = amount;
			this.unit = unit;
		}
		public int getAmount() {
			return this.amount;
		}
		public String getUnit() {
			return this.unit;
		}
	}
}