package com.currency.exchange.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONMLParserConfiguration;
import org.json.JSONObject;

import com.currency.exchange.util.ParameterStringBuilder;

public class ChangeCurrency {

	public static void main(String[] args) {
		
		// Fetch list of Currencies to show on Panel
		String[] currencies = {"KWD", "BHD", "OMR", "JOD", "KYD", "CHF", "EUR", "USD", "CAD", "INR"};
		List<String> supportedCurrencies = Arrays.asList(currencies);
		int counter = 0;
		int cur1 = 0;
		int cur2 = 0;
		BigDecimal amount = new BigDecimal(0);
		BigDecimal convertedAmount = new BigDecimal(0);
		String curSymbol1 = null;
		String curSymbol2 = null;
		int listSize = supportedCurrencies.size();
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("##################### Welcome to Exchange Expert #####################");
		System.out.println("Select currency type for conversion:");
		// Display list of currencies
		for(String symbol: supportedCurrencies) {
			counter++;
			System.out.println(counter+". "+symbol);
		}
		
		
		
		while((cur1<=0 || cur1>listSize) || (cur2<=0 || cur2>listSize) || cur1 == cur2) {
			// Take currency 1 as input
			System.out.print("Currency 1 [Select from 1 to 10]: ");
			cur1 = scan.nextInt();
			
			// Take currency 2 as input
			System.out.print("\nCurrency 2 [Select from 1 to 10]: ");
			cur2 = scan.nextInt();
			
			// Take amount to convert
			System.out.print("\nEnter amount for conversion: ");
			amount = scan.nextBigDecimal();
		}
		System.out.println(cur1+" "+cur2+" "+amount);
		curSymbol1 = supportedCurrencies.get(cur1-1);
		curSymbol2 = supportedCurrencies.get(cur2-1);
		System.out.println(curSymbol1+" "+curSymbol2+" "+amount);
		// Need to implement exception handling for null, zero or out of index error to proceed further
		convertedAmount = getConvertedAmount(curSymbol1, curSymbol2, amount);
		
		System.out.println("The exchange amount is Rs."+convertedAmount);
		
	}
	
	// Method: taking input from the user
	/*
	static double getConvertedAmount(String currencyOne, String currencyTwo, double amount) {
		System.out.println("STAGE 1: Inside getConvertedAmount");
		double convertedAmount=0d;
		String apiUrl = "https://api.freecurrencyapi.com/v1/latest";
		URL url = null;
		HttpsURLConnection con = null;
		
		
		try {
			System.out.println("STAGE 2: Inside try block");
			url = new URL(apiUrl);
			con = (HttpsURLConnection) url.openConnection();
			System.out.println("STAGE 3: Connection created");
			con.setRequestMethod("GET");
			
			Map<String, String> parameters = new HashMap<>();
			parameters.put("apikey", "DzE9j6baYbAttkoiGoWIvHO2DbetE62tY4elb93c");
			parameters.put("currencies", currencyOne);
			parameters.put("base_currency", currencyTwo);
			
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
			out.flush();
			out.close();
			System.out.println("STAGE 4: request sent");
			String contentType = con.getHeaderField("Content-Type");
			
			System.out.println("STAGE 4.5: Content Type-"+contentType);
			
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			System.out.println("STAGE 5: timeout set");
			int status = con.getResponseCode();
			System.out.println("STAGE 6: Status:"+status);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			System.out.println("STAGE 6.5: Input read");
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			System.out.println("STAGE 7 Output:"+ content.toString());

			in.close();
			con.disconnect();
			
			System.out.println("STAGE 8: Connection closed successfully");
		} catch (Exception ex) {
			System.out.println("Exception occured: "+ex.getMessage());
			con.disconnect();
		} finally {
			if(con != null) {
				con.disconnect();
			}
		}
		return convertedAmount;
	}*/

	static BigDecimal getConvertedAmount(String currencyOne, String currencyTwo, BigDecimal balance) {
		System.out.println("STAGE 1: Inside getConvertedAmount");
		BigDecimal convertedAmount= new BigDecimal(0);
		String apiUrl = "https://api.freecurrencyapi.com/v1/latest?apikey=DzE9j6baYbAttkoiGoWIvHO2DbetE62tY4elb93c&currencies="+currencyTwo;
		
		
		try {
			System.out.println("STAGE 2: Inside try block");
			
			/*
			Map<String, String> parameters = new HashMap<>();
			parameters.put("apikey", "DzE9j6baYbAttkoiGoWIvHO2DbetE62tY4elb93c");
			parameters.put("currencies", currencyOne);
			parameters.put("base_currency", currencyTwo);
			*/
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(apiUrl))
					//.headers("apikey", "DzE9j6baYbAttkoiGoWIvHO2DbetE62tY4elb93c", "currencies", currencyOne)//, "base_currency",currencyTwo)
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			
			System.out.println("Request is: "+ request.toString());

			HttpResponse<String> response = null;
			
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				
				System.out.println(response.body());
				
				JSONObject jObject = new JSONObject(response.body());
				
				JSONObject data = jObject.getJSONObject("data"); // get data object
				BigDecimal currecyRate = data.getBigDecimal("INR");
				
				System.out.println("Currency Rate:"+currecyRate);
				
				convertedAmount = currecyRate.multiply(balance);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(response.body());
			System.out.println("STAGE 8: Connection closed successfully");
		} catch (Exception ex) {
			System.out.println("Exception occured: "+ex.getMessage());
		} 
		
		return convertedAmount;
	}

}
