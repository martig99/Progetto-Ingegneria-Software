package it.unipr.java.main;


import java.util.Optional;

import it.unipr.java.model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

/**
 * The class {@code RacesController} supports the display of all races. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class RacesController {

	private App app;
	
	@FXML
	private Text info;

	@FXML
    private TableView<Race> racesTable;

    @FXML
    private TableColumn<Race, Number> idColumn, boatsNumberColumn, registrationFeeColumn;
    
    @FXML
    private TableColumn<Race, String> nameColumn, placeColumn, dateRaceColumn;
    
    //@FXML
    //private TableColumn<Race, Date> dateRaceColumn;
    
    @FXML
    private Button addButton;
    
    @FXML
    private void initialize() {	
		this.setTable();
		
			this.addButton.setOnMouseClicked(event -> {    		
				if (this.app.getLoggedUser() instanceof Employee)
					this.app.initUpsertRace(null);
	        });
			
			this.racesTable.setOnMouseClicked(event -> {
				if (this.app.getLoggedUser() instanceof Employee)
				{
					if (event.getClickCount() == 2 && this.racesTable.getSelectionModel().getSelectedItem() != null) {
						this.removeRace();
					}
	    		
					if (event.getButton() == MouseButton.SECONDARY  && this.racesTable.getSelectionModel().getSelectedItem() != null) {
						int id = this.racesTable.getSelectionModel().getSelectedItem().getId();
						this.app.initUpsertRace(id);
					}
				}
	        });
    }
    
    /**
	 * Sets the races table with columns id, name, place, data, boats number and registration fee.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		this.placeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlace()));
		this.dateRaceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateRace().toString()));
		this.boatsNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBoatsNumber()));		
		this.registrationFeeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRegistrationFee()));		
	}
    
    /**
	 * Inserts the data of each race in the table.
	**/
    public void setTableContent() {    	
    	ObservableList<Race> races = FXCollections.<Race>observableArrayList();
    	
        races.addAll(this.app.getClub().getAllRaces());
        
        this.racesTable.refresh();
		this.racesTable.setItems(races);
    }
    
    /**
     * Removes a selected race from the table. 
    **/
    public void removeRace() {
    	int id = this.racesTable.getSelectionModel().getSelectedItem().getId();
    	
    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a race", "You are removing the race with unique identifier " + id, "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.app.getClub().removeRace(id);
    		this.setTableContent();
    		
    		this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The race has been removed correctly.");
    	}
    }
    
    /**
     * Sets the reference to the app application.
     * 
     * @param app the reference to the app.
    **/
    public void setMain(final App app) {
        this.app = app;
        
        if (this.app.getLoggedUser() instanceof Employee) {
        	this.info.setText("Double click to delete a race.\nRight click to update a race.");   
        }
        else {
        	this.info.setText(""); 
        	this.app.setVisibleElement(addButton, false);
        }
        	
               
        this.setTableContent();
    }
}
