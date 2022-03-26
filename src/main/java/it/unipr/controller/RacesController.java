package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.fxml.*;
import javafx.util.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

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
    private TableColumn<Race, String> nameColumn, placeColumn, dateColumn, endDateRegistrationColumn;
    
    @FXML
    private TableColumn<Race, Button> viewRegistrationsColumn;

    @FXML
    private Button addButton;
    
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.addButton.setOnMouseClicked(event -> {    		
    		this.app.initUpsertRace(null);
        });
		
		this.racesTable.setOnMouseClicked(event -> {
			if (this.app.getLoggedUser() instanceof Employee) {
				Race race = this.racesTable.getSelectionModel().getSelectedItem();
				if (race != null && this.checkOpenRace(race.getEndDateRegistration())) {	
					if (event.getClickCount() == 2) {
						this.removeRace(race);
					}
	    		
					if (event.getButton() == MouseButton.SECONDARY) {
						this.app.initUpsertRace(race);
					}
				}
			}
        });
		
		this.racesTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
    		if (event.getCode() == KeyCode.SPACE) {
    			Race race = this.racesTable.getSelectionModel().getSelectedItem();
    			if (race != null && this.checkOpenRace(race.getEndDateRegistration())) {		
    				this.app.initUpsertRaceRegistration(race, null);
    	    	}
            }
        });
    }
    
    /**
     * 
     * @param race
     * @return
    **/
    public boolean checkOpenRace(final Date date) {
    	Object obj = ClientHelper.getResponse(new Request(RequestType.CHECK_OPEN_REGISTRATION, date));
		if (obj instanceof Response) {
			Response response = (Response) obj;
			
			if (response.getObject() != null && response.getObject() instanceof Boolean) {
				return (boolean) response.getObject();
			} else if (response.getResponseType() != null) {
				return this.app.getMessage(response.getResponseType());
			}
		}
		
		return false;
    }
    
    /**
     * Removes a selected race from the table. 
    **/
    public void removeRace(final Race race) {
       	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a race", "You are removing the race with unique identifier " + race.getId(), "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.app.getMessage(ClientHelper.getResponseType(new Request(RequestType.REMOVE_RACE, race)));
    		this.setTableContent();
    	}    	
    }
    
    /**
	 * Sets the races table with columns id, name, place, date, boats number and registration fee.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		this.placeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlace()));
		this.dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getDate())));
		this.boatsNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBoatsNumber()));		
		this.endDateRegistrationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getEndDateRegistration())));
		this.registrationFeeColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(this.app.setFloatFormat(cellData.getValue().getRegistrationFee())));		

		this.viewRegistrationsColumn.setCellFactory(new Callback<TableColumn<Race, Button>, TableCell<Race, Button>>() {
            @Override
            public TableCell<Race, Button> call(final TableColumn<Race, Button> param) {
                final TableCell<Race, Button> cell = new TableCell<Race, Button>() {
                    final Button btn = new Button("Registrations");
                    
                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                        	Race race = this.getTableView().getItems().get(getIndex());                       	
                            btn.setOnAction(event -> {
                            	ResponseType result = ClientHelper.getResponseType(new Request(RequestType.EXIST_REGISTRATIONS_FOR_RACE, race));
                            	if (result == ResponseType.OK) {
                            		app.initRaceRegistrations(race);
                            	} else {
                            		app.getMessage(result);
                            	}
                            });
                            
            				btn.setStyle("-fx-background-color: transparent; -fx-border-color: #0eaae6; -fx-border-radius: 5px; -fx-text-fill: #0eaae6; -fx-font-weight: bold;");
                            setGraphic(btn);
                    	}
                    }
                };
                
                return cell;
            }
        });
	}
	
    /**
	 * Inserts the data of each race in the table.
	**/
    public void setTableContent() {    	
    	ObservableList<Race> races = FXCollections.<Race>observableArrayList();	
    	ArrayList<Race> list = ClientHelper.getListResponse(new Request(RequestType.GET_ALL_RACES), Race.class);
        races.addAll(list);
		this.racesTable.setItems(races);
    }
    
    /**
     * Sets the reference to the app application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        String textInfo = "";
        if (this.app.getLoggedUser() instanceof Employee) {
        	textInfo = "Double click to delete a race.\nRight click to update a race.\n";
        } else {
        	this.app.setVisibleElement(this.addButton, false);
        }
        
        textInfo += "Spacebar to register a boat for a race.";
        this.info.setText(textInfo);
       
        this.setTableContent();
    }
}
