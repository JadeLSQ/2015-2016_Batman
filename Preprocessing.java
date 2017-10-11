package batman;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Preprocessing {
	/* Attributes */
	public String trainPath;
	public String validPath;
	public String testPath;
	
	/* Constructor */
	public Preprocessing (String train, String valid, String test) throws Exception {
		this.trainPath = train;
		this.validPath = valid;
		this.testPath = test;
	}

	/* Methods */
	
	/** Reads and checks the files 
	 * @throws Exception **/ 
	public ArrayList<Instances> readFile() throws Exception{
		ArrayList<Instances> files = new ArrayList<Instances>(3);
		 
		DataSource dTrain = new DataSource(this.trainPath);
		DataSource dValid = new DataSource(this.validPath);
		DataSource dTest = new DataSource(this.testPath);
		
		Instances train = dTrain.getDataSet();
		Instances valid = dValid.getDataSet();
		Instances test = dTest.getDataSet();

		files.add(0, train); //l'ArrayListe contient l'instance train à l'indice 0
		files.add(1, valid);
		files.add(2, test);

		return files;
	}
	
	/** Saves the files as .zip **/
	public  void saveFile(String zipFile ,File valid, File test) throws IOException{
		
			
		FileOutputStream dest = new FileOutputStream(zipFile);
		ZipOutputStream zipOut = new ZipOutputStream(dest);

		
		addToZip(valid, zipOut);
		addToZip(test, zipOut);
		
		zipOut.close();
		dest.close();
		
		
	}
	public static void addToZip(File file, ZipOutputStream zo) throws IOException{
		FileInputStream fileIn = new FileInputStream(file);
		ZipEntry zE = new ZipEntry(file.getName());
		zo.putNextEntry(zE);
		
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fileIn.read(bytes)) >= 0) {
			zo.write(bytes, 0, length);
		}
		//zo.close();
		//fileIn.close();
		
		
		
	}
}