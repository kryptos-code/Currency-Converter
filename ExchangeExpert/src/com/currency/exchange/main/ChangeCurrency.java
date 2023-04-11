package com.currency.exchange.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.currency.exchange.util.ParameterStringBuilder;

public class ChangeCurrency {

	public static void main(String[] args) {
		
		// Fetch list of Currencies to show on Panel
		String[] currencies = {"KWD", "BHD", "OMR", "JOD", "KYD", "CHF", "EUR", "USD", "CAD", "INR"};
		List<String> supportedCurrencies = Arrays.asList(currencies);
		int counter = 0;
		int cur1 = 0;
		int cur2 = 0;
		double amount = 0d;
		double convertedAmount = 0d;
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
			amount = scan.nextDouble();
		}
		System.out.println(cur1+" "+cur2+" "+amount);
		curSymbol1 = supportedCurrencies.get(cur1-1);
		curSymbol2 = supportedCurrencies.get(cur2-1);
		
		// Need to implement exception handling for null, zero or out of index error to proceed further
		convertedAmount = getConvertedAmount(curSymbol1, curSymbol2, amount);
		
	}
	
	// Method: taking input from the user
	
	static double getConvertedAmount(String currencyOne, String currencyTwo, double amount) {
		System.out.println("STAGE 1: Inside getConvertedAmount");
		double convertedAmount=0d;
		String apiUrl = "https://api.freecurrencyapi.com";
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
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
			out.flush();
			out.close();
			System.out.println("STAGE 4: request sent");
//			con.setRequestProperty("Content-Type", "application/json");
//			con.setConnectTimeout(5000);
//			con.setReadTimeout(5000);
			System.out.println("STAGE 5: timeout set");
			int status = con.getResponseCode();
			System.out.println("STAGE 6: Status:"+status);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			System.out.println("Output:"+ content.toString());

			in.close();
			con.disconnect();
			
		} catch (Exception ex) {
			System.out.println("Exception occured: "+ex.getMessage());
			con.disconnect();
		} finally {
			if(con != null) {
				con.disconnect();
			}
		}
		return convertedAmount;
	}

}
