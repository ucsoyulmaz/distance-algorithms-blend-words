package execution;
import java.io.*;
import java.util.Scanner; 

public class ExecuterLocal {
	
	public static void main(String[] args) throws IOException {
      
		File fileDictionary = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/dict.txt");
		File fileCandidates = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates.txt");
		Scanner scCandidates = new Scanner(fileCandidates);
		scCandidates.useDelimiter("\n");
		
		char vowels [] = {'a', 'e', 'i', 'u', 'o'}; 
		
		//------------- Found Words -----------------------
		//------------- ----------- -----------------------
		File fout = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates2-results.txt");
		if(fout.exists()){
			fout.delete();
			try {
				fout.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bwOnlyFoundWords = new BufferedWriter(new OutputStreamWriter(fos));
		
		int numberOfTrues = 0;
		
		//------------- ----------- -----------------------
		
		
		//------------- Found Words with components -----------------------
		//------------- ----------- ---------------------------------------
		File fout2 = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates2-results-with-components.txt");
		if(fout2.exists()){
			fout2.delete();
			try {
				fout2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fos2 = new FileOutputStream(fout2);
		BufferedWriter bwFoundWordsComponents = new BufferedWriter(new OutputStreamWriter(fos2));
		
		int numberOfTypos = 0;
		int numberOfNotBlendWords = 0;
		
		//------------- ----------- -----------------------
		
		//------------- Not Found Words  -----------------------
		//------------- ----------- ---------------------------------------
		File fout3 = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates2-results-not-found.txt");
		if(fout3.exists()){
			fout3.delete();
			try {
				fout3.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fos3 = new FileOutputStream(fout3);
		BufferedWriter bwNotFoundWords = new BufferedWriter(new OutputStreamWriter(fos3));
		
		//------------- ----------- -----------------------
		
		//------------- Results -----------------------
		//------------- ----------- -----------------------
		File fout4 = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/candidates2-stats.txt");
		if(fout4.exists()){
			fout4.delete();
			try {
				fout4.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fos4 = new FileOutputStream(fout4);
		BufferedWriter bwStats = new BufferedWriter(new OutputStreamWriter(fos4));
		
		int numberOfWords = 0;
		
		//------------- ----------- -----------------------
		
		while(scCandidates.hasNextLine()) {
			numberOfWords++;
			System.out.println(numberOfWords);
			
			String candidate = scCandidates.nextLine();
			
			if(isProbablyTypo(candidate) == false) {
				boolean hasVowelCandidate = false;
				for(int i = 0; i < candidate.length(); i++) {
					if(candidate.charAt(i) == vowels[0]
						|| candidate.charAt(i) == vowels[1]
						|| candidate.charAt(i) == vowels[2]	
						|| candidate.charAt(i) == vowels[3]
						|| candidate.charAt(i) == vowels[4]) {
						
						hasVowelCandidate = true;
					}
				}
			
				String theMostSimilarFront = "";
				int theMostSimilarDistanceFront = -100000;
				
				
				Scanner scDictionaryJaro = new Scanner(fileDictionary);
				scDictionaryJaro.useDelimiter("\n"); 
				
				while(scDictionaryJaro.hasNextLine()) {
					String dictionaryElementJaro = scDictionaryJaro.nextLine();
					scDictionaryJaro.useDelimiter("\n"); 
					
					boolean hasVowelDictionary = false;
					for(int i = 0; i < dictionaryElementJaro.length() && hasVowelDictionary != true; i++) {
						if(dictionaryElementJaro.charAt(i) == vowels[0]
							|| dictionaryElementJaro.charAt(i) == vowels[1]
							|| dictionaryElementJaro.charAt(i) == vowels[2]	
							|| dictionaryElementJaro.charAt(i) == vowels[3]
							|| dictionaryElementJaro.charAt(i) == vowels[4]) {
							
							hasVowelDictionary = true;
						}
					}
					
					boolean hasConsonantDictionary = false;
					for(int i = 0; i < dictionaryElementJaro.length() && hasConsonantDictionary != true; i++) {
						if(dictionaryElementJaro.charAt(i) != vowels[0]
							&& dictionaryElementJaro.charAt(i) != vowels[1]
							&& dictionaryElementJaro.charAt(i) != vowels[2]	
							&& dictionaryElementJaro.charAt(i) != vowels[3]
							&& dictionaryElementJaro.charAt(i) != vowels[4]) {
							
							hasConsonantDictionary = true;
						}
					}
					
					if(dictionaryElementJaro.charAt(0) == candidate.charAt(0) && hasVowelDictionary == true 
							&& hasVowelCandidate == true && hasConsonantDictionary == true && dictionaryElementJaro.length() > 2) {
						
						int jaroTemp = 0;
						jaroTemp = localEditDistance(candidate.substring(0,candidate.length() * 6 / 10), dictionaryElementJaro);
						
						
						if(jaroTemp > theMostSimilarDistanceFront) {
							theMostSimilarDistanceFront = jaroTemp;
							theMostSimilarFront = dictionaryElementJaro;
						}
						
					}
				}
				
				scDictionaryJaro.close();
				
				
				int mostSimilarFrontIndex = 0;
				
				
				boolean flag = true;
				
				int minLengthString = theMostSimilarFront.length() >= candidate.length() ? candidate.length() : theMostSimilarFront.length();
				
				for(int i = 0; i < minLengthString && flag != false; i++) {
					if(theMostSimilarFront.charAt(i) == candidate.charAt(i)) {
						mostSimilarFrontIndex++;
					}
					else {
						flag = false;
					}
				}
				
				//--------------------------------------------------------------------------------------------
				//--------------------------------------------------------------------------------------------
				
				Scanner scDictionaryJaro2 = new Scanner(fileDictionary);
				scDictionaryJaro2.useDelimiter("\n"); 
				
				String theMostSimilarRear = "";
				int theMostSimilarDistanceRear = -100000;
				
				while(scDictionaryJaro2.hasNextLine()) {
					String dictionaryElementJaro = scDictionaryJaro2.nextLine();
					scDictionaryJaro2.useDelimiter("\n"); 
					
					boolean hasVowelDictionary = false;
					for(int i = 0; i < dictionaryElementJaro.length() && hasVowelDictionary != true; i++) {
						if(dictionaryElementJaro.charAt(i) == vowels[0]
							|| dictionaryElementJaro.charAt(i) == vowels[1]
							|| dictionaryElementJaro.charAt(i) == vowels[2]	
							|| dictionaryElementJaro.charAt(i) == vowels[3]
							|| dictionaryElementJaro.charAt(i) == vowels[4]) {
							
							hasVowelDictionary = true;
						}
					}
					
					boolean hasConsonantDictionary = false;
					for(int i = 0; i < dictionaryElementJaro.length() && hasConsonantDictionary != true; i++) {
						if(dictionaryElementJaro.charAt(i) != vowels[0]
							&& dictionaryElementJaro.charAt(i) != vowels[1]
							&& dictionaryElementJaro.charAt(i) != vowels[2]	
							&& dictionaryElementJaro.charAt(i) != vowels[3]
							&& dictionaryElementJaro.charAt(i) != vowels[4]) {
							
							hasConsonantDictionary = true;
						}
					}
					
					if(dictionaryElementJaro.charAt(dictionaryElementJaro.length() - 1) == candidate.charAt(candidate.length() - 1) 
							&& hasVowelDictionary == true && hasVowelCandidate == true && hasConsonantDictionary == true
							&& dictionaryElementJaro.length() > 2 && candidate.substring(mostSimilarFrontIndex).length() <= dictionaryElementJaro.length()) {
						
						int jaroTemp = 0;
						jaroTemp = localEditDistance(candidate.substring(mostSimilarFrontIndex), dictionaryElementJaro);
						
						
						if(jaroTemp > theMostSimilarDistanceRear) {
							theMostSimilarDistanceRear = jaroTemp;
							theMostSimilarRear = dictionaryElementJaro;
						}
					}
				}
				
				scDictionaryJaro2.close();
				
				
				
				boolean isBlended = isBlendWord(candidate, theMostSimilarFront, theMostSimilarRear, mostSimilarFrontIndex);
				String resultRow = candidate + " " + theMostSimilarFront + " " + theMostSimilarRear + " ---> " + isBlended; 
				
				if(isBlended == true) {
					numberOfTrues++;
					
					bwOnlyFoundWords.write(candidate);
					bwOnlyFoundWords.newLine();
					
					bwFoundWordsComponents.write(resultRow);
					bwFoundWordsComponents.newLine();
				}
				else {
					bwNotFoundWords.write(resultRow);
					bwNotFoundWords.newLine();
					numberOfNotBlendWords++;
				}
				
			}
			else {
				bwNotFoundWords.write(candidate + " -- TYPO -- " + false);
				bwNotFoundWords.newLine();
				numberOfTypos++;
				numberOfNotBlendWords++;
			}
			
		}
		
		bwStats.write("True Blend::" + numberOfTrues);
		bwStats.newLine();
		bwStats.write("Not Blend::" + numberOfNotBlendWords);
		bwStats.newLine();
		bwStats.write("Typo Count::" + numberOfTypos);
		bwStats.newLine();
		bwStats.write("Total Words::" + numberOfWords);
		
		bwOnlyFoundWords.close();
		bwFoundWordsComponents.close();
		bwNotFoundWords.close();
		bwStats.close();
	
    }
	
	public static boolean isProbablyTypo(String candidate) throws FileNotFoundException {
		File fileDictionary = new File("/Users/cemsoyulmaz/desktop/KT Assignment 1/KT - Assignment 1/src/documents/dict.txt");
		
		Scanner scDictionaryTypo = new Scanner(fileDictionary);
		scDictionaryTypo.useDelimiter("\n"); 
		
		int theMostSimilarDistance = -100000;
		String theMostSimilarWord = "";
		
		while(scDictionaryTypo.hasNextLine()) {
			
			
			String dictionaryElementTypo = scDictionaryTypo.nextLine();
			scDictionaryTypo.useDelimiter("\n"); 
			
			int globalDistance = globalEditDistance(candidate, dictionaryElementTypo);
			
			if(globalDistance > theMostSimilarDistance) {
				theMostSimilarDistance = globalDistance;
				theMostSimilarWord = dictionaryElementTypo;
			}
			
		}
		
		
		if(candidate.length() - theMostSimilarDistance == 1) {
			return true;
		}
		else {
			if(candidate.length() - theMostSimilarDistance == 3) {
				return isOnlyOneTransposeRequired(candidate, theMostSimilarWord);
			}
			else if(candidate.length() - theMostSimilarDistance == 2) {
				if(candidate.length() - theMostSimilarWord.length() == 1) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
	}
	
	public static boolean isOnlyOneTransposeRequired(String s1, String s2) throws FileNotFoundException{
		
		if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
	        return false;
	    }
		
		if(s1.length() != s2.length()) {
			return false;
		}
	    
	    int changeRequiredIndex = 0; 
	    int amountOfChangeRequired = 0;
	    
	    for(int i = 0; i < s1.length() && amountOfChangeRequired < 3; i++) {
	    	if(s1.charAt(i) != s2.charAt(i)) {
	    		changeRequiredIndex = i;
	    		amountOfChangeRequired++;
	    	}
	    }
	    if(amountOfChangeRequired < 3) {
	    	String firstNewString = s1.substring(0,changeRequiredIndex-1) + s1.charAt(changeRequiredIndex) + s1.charAt(changeRequiredIndex - 1) + s1.substring(changeRequiredIndex + 1);
		    if(firstNewString.contentEquals(s2)) {
		    	return true;
		    }
		    else {
		    	return false;
		    }
	    }
	    else {
	    	return false;
	    }
	    
	    
	    
	}
	
	public static int findMinIndex(int [] arr) {
		
		int minIndex = 0;
		int minValue = arr[minIndex];
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] < minValue) {
				minValue = arr[i];
				minIndex = i;
			}
		}
		
		return minIndex;
	}
	
	public static boolean isBlendWord(String candidate, String theMostSimilarFront, String theMostSimilarRear, int mostSimilarFrontIndex) {
		
		if(theMostSimilarFront.length() == 0 || theMostSimilarRear.length() == 0) {
			return false;
		}
		else {
			if(mostSimilarFrontIndex == candidate.length() - 1) {
				return false;
			}
			else {
				//System.out.println(theMostSimilarRear.length() + " " + candidate.substring(mostSimilarFrontIndex).length());
				//System.out.println(candidate.substring(mostSimilarFrontIndex));
				if(theMostSimilarRear.substring(theMostSimilarRear.length() - candidate.substring(mostSimilarFrontIndex).length()).equals(candidate.substring(mostSimilarFrontIndex))) {
					return true;
				}
				else {
					return false;
				}
			}
		}
	}
	
	public static int globalEditDistance(String word1, String word2) {
		int insertPoint = -1;
		int replacePoint = -1;
		int deletePoint = -1;
		int matchPoint = 1;
		
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		dp[0][0] = 0;
		
		for (int i = 1; i <= len1; i++) {
			dp[i][0] = i * insertPoint;
		}
	 
		for (int j = 1; j <= len2; j++) {
			dp[0][j] = j * deletePoint;
		}
	 
		//iterate though, and check last char
		for (int i = 1; i <= len1; i++) {
			char c1 = word1.charAt(i - 1);
			for (int j = 1; j <= len2; j++) {
				char c2 = word2.charAt(j - 1);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i][j] = dp[i - 1][j - 1] + matchPoint;
				} else {
					int replace = dp[i - 1][j - 1] + replacePoint;
					int insert = dp[i - 1][j] + insertPoint;
					int delete = dp[i][j - 1] + deletePoint;
	 
					int max = replace < insert ? insert : replace;
					max = delete < max ? max : delete;
					dp[i][j] = max;
				}
			}
		}
	 
		
		return dp[len1][len2];
	}
	
	public static int localEditDistance(String word1, String word2) {

		int insertPoint = -1;
		int replacePoint = -1;
		int deletePoint = -1;
		int matchPoint = 1;
		
		int len1 = word1.length();
		int len2 = word2.length();
		
		int maxValue = 0;
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		dp[0][0] = 0;
		
		for (int i = 1; i <= len1; i++) {
			dp[i][0] = 0;
		}
	 
		for (int j = 1; j <= len2; j++) {
			dp[0][j] = 0;
		}
	 
		//iterate though, and check last char
		for (int i = 1; i <= len1; i++) {
			char c1 = word1.charAt(i - 1);
			for (int j = 1; j <= len2; j++) {
				char c2 = word2.charAt(j - 1);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i][j] = dp[i - 1][j - 1] + matchPoint;
				} else {
					int replace = dp[i - 1][j - 1] + replacePoint;
					int insert = dp[i - 1][j] + insertPoint;
					int delete = dp[i][j - 1] + deletePoint;
	 
					int max = replace < insert ? insert : replace;
					max = delete < max ? max : delete;
					max = max < 0 ? 0 : max;
					dp[i][j] = max;
				}
				
				maxValue = maxValue < dp[i][j] ? dp[i][j] : maxValue;
			}
		}
	 
		
		
		return maxValue;
	}
}