*****************************************************************************

---- INDEX ----

	- Files
	- Used External Methods
	- My Own Methods
	- Program Flow

*****************************************************************************

---- FILES ----

1) candidates-results.txt 
	- The one which the program displays detected blend words (not with the components)
	
2) candidates-results-not-found.txt
	- The one which the program displays the words which are not blend including:
		- The words which are possibly typo errors
		- The words which are not potentially blend words although there is no typo
		
3) candidates-stats.txt
	- The one which displays the statistical information about the program including:
		- Total number of detected blend words
		- Total number of words which may have typo
		- Total number of words which are not blend ( it includes typo as well )
		- Total number of words in the candidate.txt

4) candidates-results-with-components.txt ( ADDITIONAL - not used )
	-The one which the program displays the possible components of detected blend words.


*****************************************************************************

---- USED EXTERNAL METHODS ----

1) Global Edit Distance Method --> public static int globalEditDistance(String word1, String word2)
	It is retrieved from --> https://www.programcreek.com/2013/12/edit-distance-in-java/
	I updated it to make it compatible with what we learned in the lecture
	
	It gets 2 strings to calculate their global edit distance and returns the distance value.
	
2) Local Edit Distance Method --> public static int localEditDistance(String word1, String word2)
	It is retrieved from --> https://www.programcreek.com/2013/12/edit-distance-in-java/
	This method was prepared for Global Edit Distance in this source, I updated it to make it Local Edit Distance Algorithm
	
	It gets 2 strings to calculate their local edit distance and returns the length of longest substring between these two strings.
	
3) Jaro Winkler Similarity Method --> public static double jaroWinkler(String s1, String s2)
	It is retrieved from --> https://gist.github.com/thotro/af2dcbcf6bd7ecd9f5fc
	I updated it to make it compatible with what we learned in the lecture
	
	It gets 2 strings to calculate their Jaro Winkler Similarity and returns the similarity value in double format.

*****************************************************************************

---- MY OWN METHODS ----
1) Main method is the brain of whole program, it will be explained in the next section called "---- PROGRAM FLOW ----"
	-The methods below are the supportive methods for main method.

2) public static boolean isBlendWord(String candidate, String theMostSimilarFront, String theMostSimilarRear, int mostSimilarFrontIndex)

	-This functions receives
	 - a candidate word
	 - the most similar word for the prefix side of the this candidate word (it is was calculated in main function)
	 - the most similar word for the suffix side of the this candidate word (it is was calculated in main function)
	 - the longest starting substring length between the candidate words and the most similar word for the prefix side of the this candidate word
	 
	-Checks whether it is a blend word or not with these steps:
	 - In my hypothesis, the most similar word for the prefix can not be equal to the candidate word without it is last letter
	 	Ex: candidate --> morningx ,  the most similar word for suffix --> morning , return --> false
	 	Ex: candidate --> morningxx,  the most similar word for suffix --> morning , return --> true
	 - Checks whether such a situation exists for the given parameters
	 	- If yes, it will return false
	 	- If no, it will proceed with the following steps.
	 - Finds the longest common substring between the most similar word for suffix and the candidate
	 - Removes this substring from the candidate
	 - Checks whether the remaining substring is covered in the last letters of the most similar word for the suffix
	 	- If the answer is yes, it will return true
	 	- Otherwise, it will return false
	 	
3) public static boolean isProbablyTypo(String candidate)

	- This function receives a candidate word
	- Reads the dictionary words one by one for using them in the typo evaluation during the next step
	- Based on the criteria which is explained in my hypothesis with details, it checks whether there is a chance to see a typo
	- Additionally, it also checks whether there is a typo which can be solved by changing the positions of two consecutive letter
		- Calls isOnlyOneTransposeRequired method to check this situation
			- If the answer is yes from that function, it will return false
	 		- Otherwise, it will return true
	
4) public static boolean isOnlyOneTransposeRequired(String candidate, String dictionaryWord)
	
	- This function receives two words
	- Checks each character of both words to find the mismatch
		- If the mismatch can be solved by ONLY ONE consecutive transpose operation, it will return true
		- Otherwise, it will return false
		
	Ex: candidate --> acutally  	dictionaryWord --> actually 	return true
	Ex: candidate --> acutlaly  	dictionaryWord --> actually 	return false

5) public static int findMinIndex(double [] arr) 

	- This function receives an double array
	- Finds and returns the index which has the lowest value
	
*****************************************************************************

---- PROGRAM FLOW ----

1) Program starts from main function in ExecuterJaroLocalGlobal.java
2) Firstly, the output files are created
3) Program opens candidate.txt and starts tracing the words one by one in the loop
4) Checks whether there is a potential typo or not by using isProbablyTypo function
	4.1) If there is a potential typo, it directly skips the given word and this word is evaluated as a false blend
	4.2) If there is no error, the program will proceed with the following steps
5) Checks whether the candidate word includes any vowel
	5.1) If there is no vowel, it directly skips the given word and this word is evaluated as a false blend
	5.2) If there is at least a vowel, the program will proceed with the following steps
6) Program creates 4 arrays to find the best 5 matches for Jaro Winkler similarity values
	6.1) These arrays are:
			- For the prefix side of the candidate: 1 array for storing similarity values and 1 array for storing these words
			- For the suffix side of the candidate: 1 array for storing similarity values and 1 array for storing these words
7) Programs opens dict.txt to compare the front side (prefix side) of given candidate with all the dictionary words one by one
8) First checks whether the dictionary element has at least a vowel character
	8.1) If there is no vowel, it directly skips the given dictionary word
	8.2) If there is at least a vowel, the program will proceed with the following steps
9) Checks whether the dictionary element has at least a consonant character
	9.1) If there is no consonant, it directly skips the given dictionary word
	9.2) If there is at least a consonant, the program will proceed with the following steps
10)Checks whether the first letter of both candidate and dictionary word are the same
	10.1) If they are not the same, it directly skips the given dictionary word
	10.2) If they are the same, the program will proceed with the following steps
11)Checks whether the dictionary word has more than 2 chars
	11.1) If it has less than 3 letters, it directly skips the given dictionary word
	11.2) If it has more than 2 letters, the program will proceed with the following steps
12)Program calculates Jaro Winkler similarity of candidate and dictionary word by using jaroWinkler method
13)Compares the result with value of the prefix side of candidate array
	13.1) If it is lower than the minimum of that array, it directly skips the given dictionary word
	13.2) If it is lower than the minimum of that array, the program will proceed with the following steps
14)The dictionary word and its similarity value are added to arrays by replacing with the previous minimum of this array
15)Until finishing the dictionary word check process, the steps from 8 to 14 are implemented
16)Dictionary is closed
17)The program calculates the Local Edit Distance values of each item in the prefix array
18)Chooses the one with highest value
19)Calculates the longest substring match between candidate word and the chosen word
20)Program is done with prefix side of the candidate, now it will proceed to suffix side
21)Programs opens dict.txt to compare the rear side (suffix side) of given candidate with all the dictionary words one by one
22)First checks whether the dictionary element has at least a vowel character
	22.1) If there is no vowel, it directly skips the given dictionary word
	22.2) If there is at least a vowel, the program will proceed with the following steps
23)Checks whether the dictionary element has at least a consonant character
	23.1) If there is no consonant, it directly skips the given dictionary word
	23.2) If there is at least a consonant, the program will proceed with the following steps
24)Checks whether the last letter of both candidate and dictionary word are the same
	24.1) If they are not the same, it directly skips the given dictionary word
	24.2) If they are the same, the program will proceed with the following steps
25)Checks whether the dictionary word has more than 2 chars
	25.1) If it has less than 3 letters, it directly skips the given dictionary word
	25.2) If it has more than 2 letters, the program will proceed with the following steps
26)Program creates the inverse of both candidate and dictionary word
27)Program calculates Jaro Winkler similarity of inverse candidate and inverse dictionary word by using jaroWinkler method
28)Compares the result with value of the suffix side of candidate array
	28.1) If it is lower than the minimum of that array, it directly skips the given dictionary word
	28.2) If it is lower than the minimum of that array, the program will proceed with the following steps
29)The dictionary word and its similarity value are added to arrays by replacing with the previous minimum of this array
30)Until finishing the dictionary word check process, the steps from 22 to 29 are implemented
31)Dictionary is closed
32)The program calculates the Local Edit Distance values of each item in the suffix array by removing the number of characters in suffix from the beginning of candidate
33)Chooses the one with highest value
34)Sends these chosen suffix and prefix words to isBlend method
35)isBlend method returns the result
	35.1) If the value is true, it is evaluated as a true blend
	35.2) If the value is false, it is evaluated as a false blend