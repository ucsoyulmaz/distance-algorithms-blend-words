
*****************************************************************************

This README is only for general description of my folder structure.

For getting more details about the implementation, please read 3)

*****************************************************************************

---- FOLDER STRUCTURE ----

1) documents 
	- This folder includes:
		- The provided documents by the course
		
		- The results for Methodology 1:
			-candidates2-results.txt	--->	displays predicted true blend words
			-candidates2-stats.txt		--->	includes number of predicted blend words, number of typos, number of predicted false blend words
			-candidates2-results-not-found.txt ---> displays predicted false blend words
			-candidates2-results-with-components.txt  ---> displays predicted true blend words with their possible components (additional - not used)
			
		- The results for Methodology 2:
			-candidates-results.txt	--->	displays predicted true blend words
			-candidates-stats.txt		--->	includes number of predicted blend words, number of typos, number of predicted false blend words
			-candidates-results-not-found.txt ---> displays predicted false blend words
			-candidates-results-with-components.txt  ---> displays predicted true blend words with their possible components (additional - not used)
	
2) evaluation
	- This folder contains the implementations for evaluation the results of both methodologies which were explained in the report.
	
		-For ExecuterLocal.java ---> EvaluationOnlyLocal.java
		-For ExecuterJaroLocal.java ----> EvaluationJaroLocal.java
		
	- By taking blends.txt as the reference, they calculate the statistical values for both implementations including accuracy, precision, recall, f-measure.
		- Firstly, they calculate true positives, false positives, true negatives and false negatives
		- Then they calculcate accuracy, precision, recall, f-measure one by one.
		
3) execution
	- This folder contains the implementation of 2 methodologies which were explained in the report
	
		-ExecuterLocal.java --> the program which tries to detect the blend words by using only Local Edit Distance algorithm to focus on the longest substring match
		
		-ExecuterJaroLocal.java --> before implementing the same logic in ExecuterLocal.java class, this one makes a pre-processing to narrow the number of dictionary elements
			to five by only containing the top 5 words which have the highest suffix & prefix similarities.
			
	- Both of these programs have their own README files --> both of them were explained with more details.
		-For ExecuterLocal.java ---> README_ExecuterLocal.txt
		-For ExecuterJaroLocal.java ----> README_ExecuterJaroLocal.txt

*****************************************************************************