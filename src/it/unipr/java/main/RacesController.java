package it.unipr.java.main;


import it.unipr.java.model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 * The class {@code RacesController} supports the display of all races. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class RacesController {

	private App main;
	
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
    		this.main.initAddRace();
        });
		/*
		this.racesTable.setOnMouseClicked(event -> {
    		/*if (event.getClickCount() == 2 && this.boatsTable.getSelectionModel().getSelectedItem() != null) {
				this.removeBoat();
			}
    		
			if (event.getButton() == MouseButton.SECONDARY  && this.boatsTable.getSelectionModel().getSelectedItem() != null) {
		    	int id = this.boatsTable.getSelectionModel().getSelectedItem().getId();
				this.main.initUpdateBoat(id);
			}
        });
        */
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
	 * Inserts the data of each boat in the table.
	**/
    public void setTableContent() {    	
    	ObservableList<Race> races = FXCollections.<Race>observableArrayList();
    	
        races.addAll(this.main.getClub().getAllRaces());
        
        this.racesTable.refresh();
		this.racesTable.setItems(races);
    }
    
    /**
     * Removes a selected boat from the table. 
    **/
    /*public void removeBoat() {
    	int id = this.boatsTable.getSelectionModel().getSelectedItem().getId();
    	
    	Optional<ButtonType> result = this.main.showAlert(Alert.AlertType.CONFIRMATION, "Remove a boat", "You are removing the boat with unique identifier " + id, "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.main.getClub().removeBoat(id);
    		this.setTableContent();
    		
			this.main.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The boat has been removed correctly.");
    	}
    }*/
    
    /**
     * Sets the reference to the main application.
     * 
     * @param main the reference to the main.
    **/
    public void setMain(final App main) {
        this.main = main;
        
        this.info.setText("Double click to delete a race.\nRight click to update a race.");
               
        this.setTableContent();
    }
}
