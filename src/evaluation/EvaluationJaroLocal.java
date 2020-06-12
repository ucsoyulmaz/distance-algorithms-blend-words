package evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class EvaluationJaroLocal {

	public static void main(String[] args) throws IOException {
		
		int truePositives = calculateTruePositives("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates-results.txt");
		System.out.println("Number of true positives: " + truePositives);
		int falsePositives = calculateFalsePositives("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates-results.txt");
		System.out.println("Number of false positives: " + falsePositives);
		int falseNegatives = calculateFalseNegatives("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates-results-not-found.txt");
		System.out.println("Number of false negatives: " + falseNegatives);
		int trueNegatives =calculateTrueNegatives("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates-results-not-found.txt", falseNegatives);
		System.out.println("Number of true negatives: " + trueNegatives);
		int totalNumberOfWords = calculateTotalNumberOfWords("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates.txt");
		System.out.println();
		System.out.println("Total number of words: " + totalNumberOfWords);
		
		double precision = (double) truePositives / (double) (truePositives + falsePositives);
		double recall = (double) truePositives / (double) (truePositives + falseNegatives);
		double accuracy = ((double) truePositives + (double) trueNegatives) / (double) totalNumberOfWords;
		double fMeasure = (2 * precision * recall) / (precision + recall);
		System.out.println();
		System.out.println("Accuracy is: " + accuracy);
		System.out.println("Precision is: " + precision);
		System.out.println("Recall is: " + recall);
		System.out.println("F-Measure is: " + fMeasure);
		
	}
	
	public static int calculateTruePositives(String folder) throws FileNotFoundException {
		
		File fileBlends = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/blends.txt");
		
		Scanner scBlends = new Scanner(fileBlends);
		scBlends.useDelimiter("\n");
		
		int count = 0;
		
		while(scBlends.hasNextLine()) {
			
			
			String blend = scBlends.nextLine();
			scBlends.useDelimiter("\n"); 
			
			File fileFounds = new File(folder);
			
			Scanner scFounds = new Scanner(fileFounds);
			scFounds.useDelimiter("\n");
			
			while(scFounds.hasNextLine()) {
				
				String found = scFounds.nextLine();
				scFounds.useDelimiter("\n");
				
				boolean space2 = false;
				String comparisonBlend = "";
				
				for(int i = 0; i < blend.length() && space2 != true; i++) {
					if(blend.charAt(i) != '\t') {
						comparisonBlend = comparisonBlend + blend.charAt(i);
					}
					else {
						space2 = true;
					}
				}
				
				if(found.length() <= comparisonBlend.length()) {
					if(found.equals(comparisonBlend)) {
						count++;
						//System.out.println(found);
					}
				}
			}
			scFounds.close();
		}
		scBlends.close();
		return count;
	}
	
	public static int calculateFalsePositives(String folder) throws FileNotFoundException {
		
		File fileFounds = new File(folder);
		
		int count = 0;
		
		Scanner scFounds = new Scanner(fileFounds);
		scFounds.useDelimiter("\n");
		
		while(scFounds.hasNextLine()) {
			String found = scFounds.nextLine();
			scFounds.useDelimiter("\n");
			count++;
		}
		
		int truePositives = calculateTruePositives(folder);
		count = count - truePositives;
		scFounds.close();
		
		return count;
	}

	public static int calculateFalseNegatives(String folder) throws FileNotFoundException {
		
		File fileBlends = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/blends.txt");
		
		Scanner scBlends = new Scanner(fileBlends);
		scBlends.useDelimiter("\n");
		
		int count = 0;
		
		while(scBlends.hasNextLine()) {
			
			boolean flag = false;
			String blend = scBlends.nextLine();
			scBlends.useDelimiter("\n"); 
			
			File fileNotFounds = new File(folder);
			
			Scanner scNotFounds = new Scanner(fileNotFounds);
			scNotFounds.useDelimiter("\n");
			
			while(scNotFounds.hasNextLine()) {
				
				String notFound = scNotFounds.nextLine();
				scNotFounds.useDelimiter("\n");
				
				String comparisonString = "";
				
				boolean space = false;
				for(int i = 0; i < notFound.length() && space != true; i++) {
					if(notFound.charAt(i) != ' ') {
						comparisonString = comparisonString + notFound.charAt(i);
					}
					else {
						space = true;
					}
				}
				
				boolean space2 = false;
				String comparisonBlend = "";
				
				for(int i = 0; i < blend.length() && space2 != true; i++) {
					if(blend.charAt(i) != '\t') {
						comparisonBlend = comparisonBlend + blend.charAt(i);
					}
					else {
						space2 = true;
					}
				}
				
				
				if(comparisonString.length() <= comparisonBlend.length()) {
					if(comparisonString.equals(comparisonBlend)) {
						flag = true;
						//System.out.println(comparisonString);
					}
				}
				
			}
			
			if(flag == true) {
				count++;
			}
			scNotFounds.close();
		}
		scBlends.close();
		return count;
	}
	
	public static int calculateTrueNegatives(String folder, int falseNegatives) throws FileNotFoundException {
		
		File fileFounds = new File(folder);
		
		int count = 0;
		
		Scanner scFounds = new Scanner(fileFounds);
		scFounds.useDelimiter("\n");
		
		while(scFounds.hasNextLine()) {
			String found = scFounds.nextLine();
			scFounds.useDelimiter("\n");
			count++;
		}
		
		count = count - falseNegatives;
		scFounds.close();
		
		return count;
	}

	public static int calculateTotalNumberOfWords(String folder)throws FileNotFoundException {
		File fileFounds = new File(folder);
		
		int count = 0;
		
		Scanner scFounds = new Scanner(fileFounds);
		scFounds.useDelimiter("\n");
		
		while(scFounds.hasNextLine()) {
			String found = scFounds.nextLine();
			scFounds.useDelimiter("\n");
			count++;
		}
		scFounds.close();
		
		return count;
	}
}

