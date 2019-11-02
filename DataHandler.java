/** FXStat is a simple statistics analysis application that reads in data from a file and
outputs the number of data points, the maximum, the minimum, the sum of the
data points and their mean */

// Imports for file support 
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

// Imports for ArrayList 
import java.util.ArrayList;
import java.util.*; 

// DataHandler Class 
public class DataHandler {	
	
	// class data field
	private ArrayList<Double> numbers;
	
	// Constructor which instantiates an ArrayList 
	public DataHandler() {
		this.numbers = new ArrayList<Double>(); 
	}
	
	// Getter method for array size using size method in ArrayList Class
	public int getN() {
		return numbers.size();
	}
	
	// Method which takes a file, reads the data and returns a sorted double ArrayList
	public ArrayList<Double> readData(File file) {		
		
		// Try to open the file and show it in the text area.
		try{
			numbers = new ArrayList<Double>();
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			
			// A string to store lines of text.
			String line;
			
			// Iterate through the file reading one line at a time.
			while((line = bReader.readLine()) != null) {
				Double number = Double.parseDouble(line.trim());
				numbers.add(number);
			}
			
			Collections.sort(numbers);	

			//Important: Close the file.
			bReader.close();
			
		}//try
		
		catch(IOException ioe){
			System.err.println("Error reading file:");
			ioe.printStackTrace();				
		}
		
		return numbers;
	}	
	
	//method to find the maximum value in array 
	public double getMax() {
		double maxValue = Double.NEGATIVE_INFINITY;
		for(double number : numbers) {
			if (number > maxValue) {
				maxValue = number;
			}
		}
		return maxValue; 
	}
	
	//method to find the minimum value in array 
	public double getMin() {
		double minValue = Double.MAX_VALUE;
		for(double number : numbers) {
			if (number < minValue) {
				minValue = number;
			}
		}
		return minValue;
	}
	
	//method to find the sum of values in array 
	public double getSum() {
		double sum = 0;
		for(double number : numbers) {
			sum += number;
		}
		return sum; 
	}
	
	//method to find the average of values in array 
	public double getMean() {
		if (numbers.size() == 0)
			return 0;
		double mean = getSum() / numbers.size();
		return mean;
	}
}
