package evaluation;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BlendAnalysis {

	public static void main(String[] args) throws IOException {
			
		File fileBlends = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/blends.txt");
		
		Scanner scBlends = new Scanner(fileBlends);
		scBlends.useDelimiter("\n");
		
		double totalRate = 0.0;
		int countOfItems = 0;
		
		
		while(scBlends.hasNextLine()) {
			
			countOfItems++;
			
			int countBlendLength = 0;
			
			String blend = scBlends.nextLine();
			scBlends.useDelimiter("\n"); 
			
			boolean space2 = false;
			String comparisonBlend = "";
			
			for(int i = 0; i < blend.length() && space2 != true; i++) {
				if(blend.charAt(i) != '\t') {
					comparisonBlend = comparisonBlend + blend.charAt(i);
					countBlendLength++;
				}
				else {
					space2 = true;
				}
			}
			
			int similarity = 0;
			boolean unmatch = false;
			for(int i = 0; i < blend.length() && unmatch != true; i++) {
				if(blend.charAt(i) == blend.charAt(i + countBlendLength + 1)) {
					similarity++;
				}
				else {
					unmatch = true;
				}
			}
			
			//System.out.println(similarity + "/" + countBlendLength);
			totalRate = totalRate + (double) similarity / (double) countBlendLength;
			
		}
		scBlends.close();
		
		System.out.println(totalRate / countOfItems);
	}

}
