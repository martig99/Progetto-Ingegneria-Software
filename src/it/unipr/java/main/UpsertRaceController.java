package it.unipr.java.main;

import it.unipr.java.model.*;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.DatePicker;

/**
 * The class {@code UpsertRaceController} supports the insertion of a new race. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertRaceController {
	
	private App app;
	private Integer idRace;
	
	@FXML
	private Text title, link;
	
	@FXML
    private TextField name, place, boatsNumber, registrationFee;
	
	@FXML
	private DatePicker dateRace, endDateRegistration;
	
	@FXML
    private Button upsertButton;
	
	@FXML
    private void initialize() {	
		this.upsertButton.setOnMouseClicked(event -> {    		
    		this.upsertRace();
        });
		
		this.link.setOnMouseClicked(event -> {    		
    		this.app.initRaces();
        });
    }
		
	/**
	 * 
	**/
	public void upsertRace() {		
		if (this.app.getClub().getMaxBoatsNumber() > 1) {
			
			if (this.idRace == null) {
				if (this.name.getText().isEmpty() || this.place.getText().isEmpty() || this.dateRace.getValue() == null || this.boatsNumber.getText().isEmpty() || this.registrationFee.getText().isEmpty() || this.endDateRegistration.getValue() == null) {
					this.app.showAlert(Alert.AlertType.WARNING, "Error.", null, "Please complete all fields.");
					return;
				}
			} else {
				if (this.name.getText().isEmpty() && this.place.getText().isEmpty() && this.dateRace.getValue() == null && this.boatsNumber.getText().isEmpty() && this.registrationFee.getText().isEmpty() && this.endDateRegistration.getValue() == null) {
					this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
					return;
				}
			}
			
			Integer boatsNumber = null;
			if (!this.boatsNumber.getText().isEmpty()) {
				boatsNumber = this.app.convertToInteger(this.boatsNumber.getText());	
				if (boatsNumber <= 0) {
					return;
				}
			}
			
			Float registrationFee = null;
			if (!this.registrationFee.getText().isEmpty()) {	
				registrationFee = (float) this.app.convertToDouble(this.registrationFee.getText());
				if (registrationFee <= 0) {
					return;
				}
			}
				
			String name = !this.name.getText().isEmpty() ? this.name.getText() : null;
			String place = !this.place.getText().isEmpty() ? this.place.getText() : null;
			Date dateRace = this.dateRace.getValue() != null ? new java.util.Date(java.sql.Date.valueOf(this.dateRace.getValue()).getTime()) : null;
			Date endDateRegistration = this.endDateRegistration.getValue() != null ? new java.util.Date(java.sql.Date.valueOf(this.endDateRegistration.getValue()).getTime()) : null;
			
			Date today = this.app.getZeroTimeDate(new Date());
			
			if (dateRace != null) {
				if (dateRace.before(today)) {
					this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "The date of the race must be greater than the current date.");
					return;
				}
				
				if (this.app.getClub().getRaceByDate(dateRace) != null) {
					this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Already exists a race on " + this.app.setDateFormat(dateRace));
					return;
				}
			}
			
			Boolean errorDate = false;
			if (endDateRegistration != null && endDateRegistration.before(today)) {
				errorDate = true;
			}
			
			if ((this.idRace == null || (dateRace != null && endDateRegistration != null)) && !dateRace.after(endDateRegistration)) {
				errorDate = true;
			}
			
			if (this.idRace != null) {
				Race race = this.app.getClub().getRaceById(this.idRace);
				if (race != null) {
					if ((dateRace != null && endDateRegistration == null && !dateRace.after(race.getEndDateRegistration())) || (dateRace == null && endDateRegistration != null && !race.getDate().after(endDateRegistration))) {
						errorDate = true;
					}
				}
			}
			
			if (errorDate) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "The last date to register for the race must be greater than today and less than the race.");
				return;
			}
			
			if (boatsNumber != null && (boatsNumber <= 1 || boatsNumber > this.app.getClub().getMaxBoatsNumber())) {	
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "The number of participating boats must be greater than 1 and less than or equal to " + this.app.getClub().getMaxBoatsNumber());
				return;
			}
			
			String message = "";
			if(this.idRace == null) {
				this.app.getClub().insertRace(name, place, dateRace, boatsNumber, registrationFee, endDateRegistration);
				message = "The new race has been added correctly.";
			} else {
				this.app.getClub().updateRace(this.idRace, name, place, dateRace, boatsNumber, registrationFee, endDateRegistration);
				message = "The race has been updated correctly.";
			}
			
			this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, message);
			this.app.initRaces();
		} else {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "There aren’t enough boats to create a race.");
		}
	}
	
	/**
	 * 
	 * @param id
	**/
	public void setIdRace(final Integer idRace) {
		this.idRace = idRace;
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (this.idRace == null) {
        	this.title.setText("ADD A NEW RACE");
        } else {
        	this.title.setText("UPDATE THE RACE");
        }
    }
	
}
