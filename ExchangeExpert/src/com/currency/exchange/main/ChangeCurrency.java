package com.currency.exchange.main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ChangeCurrency {

	public static void main(String[] args) {
		
		// Fetch list of Currencies to show on Panel
		String[] currencies = {"KWD", "BHD", "OMR", "JOD", "KYD", "CHF", "EUR", "USD", "CAD", "INR"};
		List<String> supportedCurrencies = Arrays.asList(currencies);
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("##################### Welcome to Exchange Expert #####################");
		
		System.out.println("Select currency type for conversion:");
		int counter = 0;
		for(String symbol: supportedCurrencies) {
			counter++;
			System.out.println(counter+". "+symbol);
		}
		System.out.print("Your input: ");
		int cur1 = scan.nextInt();
		
		System.out.println("Select currency type for conversion:");
		counter = 0;
		for(String symbol: supportedCurrencies) {
			counter++;
			System.out.println(counter+". "+symbol);
		}
		System.out.print("Your input: ");
		int cur2 = scan.nextInt();
		
		System.out.print("Enter amount for conversion: ");
		int amount = scan.nextInt();
		
		// Need to implement exception handling for null, zero or out of index error to proceed further
	}

}
