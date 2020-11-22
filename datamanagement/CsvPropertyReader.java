package edu.upenn.cit594.datamanagement;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedList;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.logging.Logger;

public class CsvPropertyReader implements Reader{
	
	String filename;
	private Logger logger = Logger.getInstance();
	
	public CsvPropertyReader(String filename) {
		this.filename = filename;
	}

	@Override
	public List<PropertyValues> getData() {
		LinkedList<PropertyValues> propList = new LinkedList<>();
		try {
			File fileF = new File(filename);
			if(!fileF.canRead()) {
				System.out.println("error: the csv propertyfile cannot be read. Please re-check the file input");
				System.exit(0);
			}
			Scanner fileReader = new Scanner(new File(filename));
			String line1 = fileReader.nextLine();
			List<String> columnHeaders = parse(line1);
			//System.out.println(columnHeaders.length + " headersLength");
			int[] columnIndex = new int[3];
			for(int i = 0; i < columnHeaders.size(); i++) {
				if(columnHeaders.get(i).contentEquals("zip_code")) {
					columnIndex[0] = i;
				}	
				else if(columnHeaders.get(i).contentEquals("market_value")) {
					columnIndex[1] = i;
				}
				else if(columnHeaders.get(i).contentEquals("total_livable_area")) {
					columnIndex[2] = i;
				}
			}
			/*
			System.out.println(columnIndex[0] + "zip");
			System.out.println(columnIndex[1] + "mkt");
			System.out.println(columnIndex[2] + "liv");
			*/
			while(fileReader.hasNextLine()) {
				String line = fileReader.nextLine();
				//System.out.println("this is the start " + line);
				List<String> column = parse(line);
				//System.out.println(column.size() + " size");
				
			
				
				/*
				for(String p : column) {
					int count = 0;
					System.out.println(p);
					count++;
				}
				*/
				String zipString = column.get(columnIndex[0]);
				String mktString = column.get(columnIndex[1]);
				String livString = column.get(columnIndex[2]);
				if(!(zipString == null) && !(mktString == null) && !(livString == null)) {
					if(!zipString.isEmpty() && !mktString.isEmpty() && !livString.isEmpty()) {
						//System.out.println(zipString + " zipString");
						//System.out.println(mktString + " mktString");
						//System.out.println(livString + " livString");
						if(zipString.length() > 5) {
							zipString = zipString.substring(0, 5);
						}
						if(zipString.length() < 5) {
							zipString = "";
						}
						try {
							double zip = Double.parseDouble(zipString);
							double mkt = Double.parseDouble(mktString);
							double liv = Double.parseDouble(livString);
							PropertyValues prop = new PropertyValues((int) mkt, (int) liv, String.valueOf((int) zip));
							//System.out.println(prop.zipCode + "testing the zip codes");
							propList.add(prop);
								//System.out.println(newFlight.toString());
						}
						catch(NumberFormatException e){
							//e.printStackTrace();
						}
					}
				}
			}
		}
		catch(FileNotFoundException e) {
			
		}
		List<PropertyValues> propList1 = propList;
		logger.logFileRead(filename);
		return propList1;
	}

	
	public List<String> parse(String text) {

		List<String> tokens = new ArrayList<String>();
		boolean insideQuotes = false;
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
		    if(text.charAt(i) == '\"') {
		        insideQuotes = !insideQuotes;
		        token.append(text.charAt(i));
		    }    
		    else if(text.charAt(i) == ',') {
		        if (insideQuotes) {
		            token.append(text.charAt(i));
		        } else {
		            tokens.add(token.toString());
		            token = new StringBuilder();
		        }
		    }
		    else {
		    	token.append(text.charAt(i));
		    }
		}
		tokens.add(token.toString());		
		return tokens;
	}

	
	
	public static void main(String[] args) {
		CsvPropertyReader c = new CsvPropertyReader("testingFile.csv");
		System.out.println(c.getData().size());
		//PropertyValues p = c.getData().get(c.getData().size()-1);
		PropertyValues p = c.getData().get(0);
			System.out.println(p.marketValue + "  mktvalue");
			System.out.println(p.totalLivableArea + "  totalLivArea");
			System.out.println(p.zipCode + "  zipCode");
	}
	
}
