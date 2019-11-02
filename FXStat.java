/** FXStat is a simple statistics analysis application that reads in data from a file and
outputs the number of data points, the maximum, the minimum, the sum of the
data points and their mean */

// Standard javafx libraries.
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

// Imports for components in this application.
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

// Imports for layout.
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import javafx.scene.layout.BorderPane;

// Imports for file support.
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;

import java.util.ArrayList;

// FXStat Class which inherits Application 
public class FXStat extends Application {
	
	// Create visual components at class scope.
	Label lblMax, lblMin, lblSum, lblMean, lblNumbers;
	
	// Create TextFields...
	TextField txtfMax, txtfMin, txtfSum, txtfMean;
	
	// Create ListView.
	ListView <String> lvNumbers;
	
	// Create MenuBar 
	MenuBar mBar;
	
	// Create File 
	File savedFile;
	
	// Create variables
	double minValue, maxValue, sumValue;
	
	// Declare DataHandler instance   
	DataHandler dataHandler;

	// Constructor 
	public FXStat() {
		
	}

	@Override
	// init method to instantiate classes  
	public void init(){		
		
		// Create variables
		minValue = 0;
		maxValue = 0;
		sumValue = 0;
		
		// Instantiate visual components at class scope.
		lblMax = new Label("Maximum:");
		lblMin = new Label("Minimum:");
		lblSum = new Label("Sum:");
		lblMean = new Label("Mean:");
		lblNumbers= new Label ("Numbers:");		
		
		// Instantiate TextFields...
		txtfMax = new TextField();
		txtfMin = new TextField();
		txtfSum = new TextField();
		txtfMean = new TextField();
		
		// Instantiate listView
		lvNumbers = new ListView <String>();
		
		// Manage listView width and Height.
		lvNumbers.setMinWidth(100);
		lvNumbers.setMinHeight(200);
		
		// Instantiate MenuBar
		mBar = new MenuBar();
		
		// Instantiate Menu
		Menu fileMnu = new Menu("File");
		
		// Add MenuItems to the FileMenu.
		MenuItem openItem = new MenuItem("Open");
		fileMnu.getItems().add(openItem);
		openItem.setOnAction(ae -> doFileOpen());
		
		MenuItem saveItem = new MenuItem("Save As...");
		fileMnu.getItems().add(saveItem);
		saveItem.setOnAction(ae -> doSaveFile());
		
		// Add FileMenu to MenuBar 
		mBar.getMenus().add(fileMnu);
		
		// Instantiate DataHandler class
		dataHandler = new DataHandler();
		
	}
		
	@Override
	// start method 
	public void start(Stage pStage) throws Exception {	
		
		// Set the title.
		pStage.setTitle("FX Statistics Analysis Application");
		
		// Set the width and height.
		pStage.setWidth(400);
		pStage.setHeight(500);
		
		// Create a layout.
		BorderPane bp = new BorderPane();	
		
		// Add components to the layout.
		bp.setTop(mBar);
		
		// Add GridPane to BorderPane to display outputs  
		GridPane gp = new GridPane();
		
		// Set gap
		gp.setHgap(10);
		gp.setVgap(20);
		
		// Set margin
		gp.setPadding(new Insets(10));
				
		// Add List view
		gp.add(lblNumbers, 0, 0);
		gp.add(lvNumbers,  0, 1, 2, 10);
		
		// Set labels and Text fields position 
		int col = 3;
		gp.add(lblMax,   col, 0);
		gp.add(txtfMax,  col, 1);
		gp.add(lblMin,   col, 2);
		gp.add(txtfMin,  col, 3);
		gp.add(lblSum,   col, 4);
		gp.add(txtfSum,  col, 5);
		gp.add(lblMean,  col, 6);
		gp.add(txtfMean, col, 7);
		
		// Add grid pane to border pane
		bp.setCenter(gp);

		// Create a scene.
		Scene s = new Scene(bp);
		
		// Set the scene.
		pStage.setScene(s);
		
		// Show the stage.
		pStage.show();
			
	}
	
	// method to open file, assign to list view and take outputs 
	public void doFileOpen(){		
		// Create a file chooser dialog.
		FileChooser fChooser = new FileChooser();
		
		// Create a file instance. Assign the file from the fc.
		savedFile = fChooser.showOpenDialog(null);
		
		// Check if the file is correctly chosen.
		if(savedFile != null){
			
			// Clear listView from previous inputs
			lvNumbers.getItems().clear();
			
			// Invoke readData from dataHandler class, assign values to an Array List
			ArrayList<Double> dataValues = dataHandler.readData(savedFile);
			
			// Assign values to list view 
			for(Double value : dataValues){
				lvNumbers.getItems().add(Double.toString(value));
			}
			
			// Take outputs from dataHandler methods
			txtfMax.setText(Double.toString(dataHandler.getMax()));
			txtfMin.setText(Double.toString(dataHandler.getMin()));
			txtfSum.setText(Double.toString(dataHandler.getSum()));
			txtfMean.setText(String.format ("%.2f", dataHandler.getMean()));
			
			//catch
		}//if fileToOpen		
	}
	
	//method to save file 
	public void doSaveFile(){
		
		//A file chooser to choose the path for the file.
		FileChooser fc = new FileChooser();
		
		savedFile = fc.showSaveDialog(null);
		
		if(savedFile != null) {
			
			//Try to save the file.
			try {
				FileOutputStream fos = new FileOutputStream(savedFile, true);
				for(String number : lvNumbers.getItems()){
					String text = number + "\n";
					byte[] dataOut = text.getBytes();
					fos.write(dataOut);
				}
				fos.flush();
				fos.close();						
				
			}//try
			catch(IOException ioe) {
				System.out.println("Error writing file:" + ioe.getMessage());
				
			}//catch
		}
	}
	
	@Override
	// Stop method
	public void stop(){

	}
	
	// Main method 
	public static void main(String[] args) {
		//Launch the application.
		launch();
	}
}