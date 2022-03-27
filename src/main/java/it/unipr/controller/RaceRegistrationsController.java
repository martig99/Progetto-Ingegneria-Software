package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.fxml.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

/**
 * The class {@code RegistrationsController} supports the display of all races. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class RaceRegistrationsController {

	private App app;
	private Race race;
	
	@FXML
	private Text title, info;

	@FXML
    private TableView<RaceRegistration> registrationsTable;

    @FXML
    private TableColumn<RaceRegistration, Number> idColumn;
    
    @FXML
    private TableColumn<RaceRegistration, String> dateColumn, boatColumn, memberColumn;
    
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.registrationsTable.setOnMouseClicked(event -> {
			if (this.registrationsTable.getSelectionModel().getSelectedItem() != null) {
				RaceRegistration registration = this.registrationsTable.getSelectionModel().getSelectedItem();
		    	if (this.checkOpenRace(this.race.getEndDateRegistration())) {
					if (event.getClickCount() == 2) {
						this.removeRegistration(registration);
					}
				
					if (event.getButton() == MouseButton.SECONDARY) {
						if (!this.checkUser(registration))
				    		return;
						
						this.app.initUpsertRaceRegistration(race, registration);
					}
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
    	Object obj = ClientHelper.getResponse(new Request(RequestType.CHECK_OPEN_REGISTRATION, date, null));
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
     * Removes a selected registration from the table. 
    **/
    public void removeRegistration(final RaceRegistration registration) { 
    	if (!this.checkUser(registration))
    		return;
    	
    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a registration", "You are removing the registration with unique identifier " + registration.getId(), "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.app.getMessage(ClientHelper.getResponseType(new Request(RequestType.REMOVE_RACE_REGISTRATION, registration, this.race)));
    		this.setTableContent(this.race);
    	}	
    }
    
    /**
     * 
     * @param registration
     * @return
    **/
    public boolean checkUser(final RaceRegistration registration) {
    	if (this.app.getLoggedUser() instanceof Member) {
	    	User user = registration.getBoat().getOwner();
			if (!user.getEmail().equals(this.app.getLoggedUser().getEmail())) {
				this.app.showAlert(Alert.AlertType.ERROR, "Error", null, "You cannot update/remove this registration to the race.");
	    		return false;
			}
    	}
    	
    	return true;
    }
    
    /**
	 * Sets the races table with columns id, name, place, date, boats number and registration fee.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getDate())));	
		
		this.boatColumn.setCellValueFactory(cellData -> {
			Boat boat = cellData.getValue().getBoat();
			if (boat != null) {
				String description = boat.getId() + " - " + boat.getName();
				return new SimpleStringProperty(description);
			}
			
			return null;
		});
		
		this.memberColumn.setCellValueFactory(cellData -> {
			Boat boat = cellData.getValue().getBoat();
			if (boat != null) {
				Member owner = boat.getOwner();
				if (owner != null) {
					return new SimpleStringProperty(owner.getEmail());
				}
			}
			
			return null;
		});
	}
    
    /**
	 * Inserts the data of each race in the table.
	**/
    public void setTableContent(final Race race) {    	
    	ObservableList<RaceRegistration> registrations = FXCollections.<RaceRegistration>observableArrayList();      	
        registrations.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_REGISTRATION_BY_RACE, race, null), RaceRegistration.class));
		this.registrationsTable.setItems(registrations);
    }
    
    /**
     * 
     * @param idRace
    **/
    public void setRace(final Race race) {
    	this.race = race;
    }
    
    /**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;

        this.info.setText("Double click to delete a registration.\nRight click to update a registration");
        this.title.setText("ALL PARTECIPANTS OF THE RACE ON " + this.app.setDateFormat(this.race.getDate()));
        
        this.setTableContent(this.race);
    }
}
