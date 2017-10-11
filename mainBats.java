package batman;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instances;

public class mainBats {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		// Getting data files
    	String ext = ".arff";
    	// you will need to modify the path
    	// this path is to your train / valid / test datasets that you downloaded
    	String startPath = "/home/Alexandrina/Documents/batman_arff_data";
    	// do not modify 
    	String trainPath = startPath + "/train" + ext;
    	String validPath = startPath + "/valid" + ext;
    	String testPath = startPath + "/test" + ext;
    	
    	String challengeName = "Batman II"; 
    	
    	System.out.println("Starting solver for " + challengeName + "...");
        
    	Preprocessing preproc = new Preprocessing(trainPath, validPath, testPath);
    	
    	// Create instances
    	Instances trainData = new Instances(new FileReader(trainPath));
    	Instances validData = new Instances(new FileReader(validPath));
    	Instances testData = new Instances(new FileReader(testPath));

    	
    	// Set the attribute to predict (the last one) in each dataset
    	int ind = trainData.numAttributes() - 1;
    	trainData.setClassIndex(ind);
    	validData.setClassIndex(ind);
    	testData.setClassIndex(ind);

    	System.out.println("Data loaded.\nStarting to evaluate classifiers...");
    	// Get the best classifiers for data
    	ArrayList<Classifier> classifiers = (new Evaluateur()).getClassifiers(
    			trainData, testData, validData, ind);

    	System.out.println("Classifier evaluation finished.");
    	System.out.println("Starting comparison...");
    	
    	//(new Comparateur()).Comp(classifiers);
    	
    	Comparateur compare = new Comparateur(classifiers, trainData, validData, testData);
    	compare.Comp();
    	
    	System.out.println("Done solving " + challengeName + ".\nCreating and zipping files...");
    	
    	//you will need to modify the path.
    	// this path should lead to the project within your workspace
    	// the path should be ../../[your workspace]/[the project name]
    	
    	String workspaceProjectPath = "/home/Alexandrina/workspace/TheBatman";
    	
    	// Do not modify 
    	
    	String finalValid = "/ref_valid.predict";
    	String finalTest = "/ref_test.predict";
    	File validRes = new File(workspaceProjectPath + finalValid);    
    	File testRes = new File(workspaceProjectPath + finalTest);    	

    	
    	preproc.saveFile("bat2_pred.zip", validRes, testRes);
    	
    	System.out.println("\nFiles written and zipped at bat2_pred.zip.");
	}
}
