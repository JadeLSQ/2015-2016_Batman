package batman;

import java.io.FileReader;
import java.io.PrintWriter;

import weka.*;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.classifiers.evaluation.NominalPrediction;
import java.io.*;
import java.util.*;

public class Comparateur {
	
//static Classifier A= new J48();
//static Classifier B= new RandomForest();
//static Classifier C= new NaiveBayes();

static boolean debugOn = false;	
	
static PrintWriter pw;
static PrintWriter pw1;

static int validSize;
static int testSize;
static Instances testData;
static Instances validData;
static Instances trainData;

private static ArrayList<Classifier> Tab= new ArrayList<Classifier>();



public Comparateur(ArrayList<Classifier> classifiers, Instances train, Instances valid, Instances test){
	this.Tab = classifiers;
	this.trainData = train;
	this.testData = test;
	this.validData = valid;
	
}

public static void Comp() throws Exception{

	System.out.println("The comparator\n" + "Classifying data for BatmanII");
	
	// Set the attribute to predict (the last one) in each dataset
	int ind = trainData.numAttributes() - 1;
	trainData.setClassIndex(ind);
	validData.setClassIndex(ind);
	testData.setClassIndex(ind);
	Attribute crimeSolved = trainData.attribute(ind);
	
		for (int i = 0; i < Tab.size(); i++) {
			Tab.get(i).buildClassifier(trainData);

			Evaluation validEval = new Evaluation(trainData);
			validEval.evaluateModel(Tab.get(i), validData);
			FastVector validPred = validEval.predictions();

			Evaluation testEval = new Evaluation(trainData);
			testEval.evaluateModel(Tab.get(i), testData);
			FastVector testPred = testEval.predictions();

			
			if(debugOn == true){
			System.out.println(" VALID and TEST sizes " + validPred.size() + " " + testPred.size());
			}
			
			validSize = validPred.size();
			testSize = testPred.size();

			pw = new PrintWriter("ref_valid" + i + ".predict", "UTF-8");
			pw1 = new PrintWriter("ref_test" + i + ".predict", "UTF-8");

			for (int j = 0; j < validPred.size(); j++) {
				double val = ((NominalPrediction) validPred.elementAt(j)).predicted();
				pw.print(crimeSolved.value((int) val) + "\n");
			}
			pw.close();

			for (int k = 0; k < testPred.size(); k++) {
				double val = ((NominalPrediction) testPred.elementAt(k)).predicted();
				pw1.print(crimeSolved.value((int) val) + "\n");
			}
			pw1.close();

			System.out.println("Files successfully created !");

		}
		
		System.out.println("File creation successful");
		
		System.out.println("Comparing results...");
		
		// declare the files where the results for each classifier were written 
		// You will need to modify the path to where the files were created
		// the files will be in the project within the workspace
		
		String fileLocation = "/home/Alexandrina/workspace/TheBatman";
		
		// do not modify
		
		File f1 = new File(fileLocation  + "/ref_valid0.predict");
		File f2 = new File(fileLocation  + "/ref_valid1.predict");
		File f3 = new File(fileLocation  + "/ref_valid2.predict");
		
		File f4 = new File(fileLocation  + "/ref_test0.predict");
		File f5 = new File(fileLocation  + "/ref_test1.predict");
		File f6 = new File(fileLocation  + "/ref_test2.predict");
		
		// writer for final file 
		//This is the name of your FINAL valid file
		PrintWriter writer = new PrintWriter("ref_valid.predict", "UTF-8");


		//valid file readers
		FileReader fR1 = new FileReader(f1);
		FileReader fR2 = new FileReader(f2);
		FileReader fR3 = new FileReader(f3);
		//test file readers
		FileReader fR4 = new FileReader(f4);
		FileReader fR5 = new FileReader(f5);
		FileReader fR6 = new FileReader(f6);
		
		//valid readers
        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);
        BufferedReader reader3 = new BufferedReader(fR3);
        //test readers
        BufferedReader reader4 = new BufferedReader(fR4);
        BufferedReader reader5 = new BufferedReader(fR5);
        BufferedReader reader6 = new BufferedReader(fR6);
        
        // compare the results of the Valid files to obtain final prediction.
        
        for(int i = 1; i<=validSize; i++){
        	int a1 = Integer.parseInt(reader1.readLine());
        	int a2 = Integer.parseInt(reader2.readLine());
        	int a3 = Integer.parseInt(reader3.readLine());
        	int myInt = (a1 != 0 || a2 !=0 || a3 !=0) ? 1 : 0;
        	if(debugOn == true) {
        		String line = a1 + " " +a2 + " " + a3 + " " + myInt;
        		writer.println(line);
        	} else {
        	writer.println(myInt);
        	}
        }
        writer.close();
        
        System.out.println(("Valid comparison complete"));
        
       
        // this is the name of your FINAL test file.       
        writer = new PrintWriter("ref_test.predict", "UTF-8");
        
        // Compare the results of the Test files to obtain final prediction 
        
        for(int i = 1; i<=testSize; i++){
        	int a1 = Integer.parseInt(reader4.readLine());
        	int a2 = Integer.parseInt(reader5.readLine());
        	int a3 = Integer.parseInt(reader6.readLine());
        	int myInt = (a1 != 0 || a2 !=0 || a3 !=0) ? 1 : 0;
        	if(debugOn == true) {
        		String line = a1 + " " +a2 + " " + a3 + " " + myInt;
        		writer.println(line);
        	} else {
        	writer.println(myInt);
        	}
        }
        writer.close();
        System.out.println("Test comparison complete");
        
        System.out.println("Comparing complete!");        
	}
}
