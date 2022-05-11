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
 * The class {@code RegistrationsController} supports the display of all race registrations. 
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
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
    
    /**
     * {@inheritDoc}
    **/
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.registrationsTable.setOnMouseClicked(event -> {
			if (this.registrationsTable.getSelectionModel().getSelectedItem() != null) {
				RaceRegistration registration = this.registrationsTable.getSelectionModel().getSelectedItem();
				if (event.getClickCount() == 2) {
					this.removeRegistration(registration);
				}
			
				if (event.getButton() == MouseButton.SECONDARY) {
					if (!this.checkUser(registration))
			    		return;
					
					this.app.initUpsertRaceRegistration(race, registration);
				}
			}
        });
    }
    
    /**
     * Removes a selected registration from the table. 
     * 
     * @param registration the selected registration.
    **/
    public void removeRegistration(final RaceRegistration registration) { 
    	if (!this.checkUser(registration))
    		return;
    	
    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a registration", "You are removing the registration with unique identifier " + registration.getId(), "Are you sure?");
    	if (result.get() == ButtonType.OK){    		
    		boolean resultMessage = this.app.isSuccessfulMessage(ClientHelper.getResponseType(new Request(RequestType.REMOVE_RACE_REGISTRATION, Arrays.asList(registration.getId(), this.race))));
    		if (!resultMessage) {
    			return;
    		}
    		
    		this.setTableContent(this.race);
    	}	
    }
    
    /**
     * Checks if the logged in user is a club member and if so checks if it is equal to the member who registered for the race.
     * 
     * @param registration the registration for the race to be considered.
     * @return <code>true</code> if the logged-in user is the member who registered for the race.
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
	 * Sets the race registrations table with columns id, date, description of the boat and email of the member.
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
				User owner = boat.getOwner();
				if (owner != null) {
					return new SimpleStringProperty(owner.getEmail());
				}
			}
			
			return null;
		});
	}
    
    /**
	 * Inserts the data of each registration of a race in the table.
	 * 
	 * @param race the race.
	**/
    public void setTableContent(final Race race) {    	
    	ObservableList<RaceRegistration> registrations = FXCollections.<RaceRegistration>observableArrayList();   	
        registrations.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_REGISTRATIONS_BY_RACE, Arrays.asList(race.getId())), RaceRegistration.class));
		this.registrationsTable.setItems(registrations);
    }
    
    /**
     * Sets the race to which the registration refer.
     * 
     * @param race the race.
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
