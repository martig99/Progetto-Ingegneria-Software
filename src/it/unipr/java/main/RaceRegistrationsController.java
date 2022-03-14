package it.unipr.java.main;

import it.unipr.java.model.*;

import java.util.Date;
import java.util.Optional;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

/**
 * The class {@code RegistrationsController} supports the display of all races. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class RaceRegistrationsController {

	private App app;
	private int idRace;
	
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
				int idRegistration = this.registrationsTable.getSelectionModel().getSelectedItem().getId();
		    	
				if (event.getClickCount() == 2) {
					this.removeRegistration(idRegistration);
				}
			
				if (event.getButton() == MouseButton.SECONDARY) {				
					this.updateRegistration(idRegistration);
				}
			}
        });
    }
    
    /**
     * Removes a selected registration from the table. 
    **/
    public void removeRegistration(final int idRegistration) { 
    	Boolean userResult = this.userCheck(idRegistration, "You cannot delete this race registration.");
    	if (!userResult) {
    		return;
    	}
    	
    	Boolean resultDate = this.dateCheck("It is no longer possible to remove the boat from the race.");
    	if (!resultDate) {
    		return;
    	}
		
    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a registration", "You are removing the registration with unique identifier " + idRegistration, "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.app.getClub().removeRaceRegistration(idRegistration);
    		    		
    		Race race = this.app.getClub().getRaceById(this.idRace);
    		this.setTableContent(race);
    		
    		this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The registration has been removed correctly.");
    	}	
    }
    
    /**
     * Updates a selected registration from the table. 
    **/
    public void updateRegistration(final int idRegistration) {
    	Boolean resultUser = this.userCheck(idRegistration, "You cannot update this race registration.");
    	if (!resultUser) {
    		return;
    	}
    	
    	Boolean resultDate = this.dateCheck("It is no longer possible to update the boat.");
    	if (!resultDate) {
    		return;
    	}
		
		this.app.initUpsertRaceRegistration(this.idRace, idRegistration);    	
    }
    
    /**
     * 
     * @param message
     * @return
    **/
    public boolean dateCheck(final String message) {
    	Race race = this.app.getClub().getRaceById(this.idRace);
    	
    	Date today = this.app.getZeroTimeCalendar(new Date()).getTime();
    	Date endDateRegistration = this.app.getZeroTimeCalendar(race.getEndDateRegistration()).getTime();

		if (endDateRegistration.before(today)) {
    		this.app.showAlert(Alert.AlertType.WARNING, "Attention", null, message);
    		return false; 
		}
		
		return true;
    }
    
    /**
     * 
     * @param idRegistration
     * @param message
     * @return
    **/
    public boolean userCheck(final int idRegistration, final String message) {
    	if (this.app.getLoggedUser() instanceof Member) {
			RaceRegistration registration = this.app.getClub().getRaceRegistrationById(idRegistration);
			User user = registration.getBoat().getOwner();
			
			if (!user.getEmail().equals(this.app.getLoggedUser().getEmail())) {
				this.app.showAlert(Alert.AlertType.ERROR, "Error", null, message);
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
    	registrations.addAll(this.app.getClub().getAllRegistrationsByRace(race));
        
        this.registrationsTable.refresh();
		this.registrationsTable.setItems(registrations);
    }
    
    /**
     * 
     * @param idRace
    **/
    public void setIdRace(final int idRace) {
    	this.idRace = idRace;
    }
    
    /**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;

        this.info.setText("Double click to delete a registration.\nRight click to update a registration");
        
        Race race = this.app.getClub().getRaceById(this.idRace);
        this.title.setText("ALL PARTECIPANTS OF THE RACE ON " + this.app.setDateFormat(race.getDate()));
        
        this.setTableContent(race);
    }
}
