package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * The class {@code UpsertRaceController} supports the insertion of a new race. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertRaceController { 
	
	private App app;
	private Race race;
	
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
		if (this.race == null) {
			if (this.name.getText().trim().isEmpty() || this.place.getText().trim().isEmpty() || this.dateRace.getValue() == null || this.boatsNumber.getText().trim().isEmpty() || this.registrationFee.getText().trim().isEmpty() || this.endDateRegistration.getValue() == null) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error.", null, "Please complete all fields.");
				return;
			}
		} else {
			if (this.name.getText().trim().isEmpty() && this.place.getText().trim().isEmpty() && this.dateRace.getValue() == null && this.boatsNumber.getText().trim().isEmpty() && this.registrationFee.getText().trim().isEmpty() && this.endDateRegistration.getValue() == null) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
				return;
			}
		}
		
		int boatsNumber = 0;
		if (!this.boatsNumber.getText().isEmpty()) {
			boatsNumber = this.app.convertToInteger(this.boatsNumber.getText());	
			if (boatsNumber <= 0) {
				return;
			}
		} else {
			boatsNumber = this.race.getBoatsNumber();
		}
		
		float registrationFee = 0;
		if (!this.registrationFee.getText().isEmpty()) {	
			registrationFee = (float) this.app.convertToFloat(this.registrationFee.getText());
			if (registrationFee <= 0) {
				return;
			}
		} else {
			registrationFee = this.race.getRegistrationFee();
		}
			
		int idRace = this.race == null ? 0 : this.race.getId();
		String name = !this.name.getText().isEmpty() ? this.name.getText() : null;
		String place = !this.place.getText().isEmpty() ? this.place.getText() : null;
		Date dateRace = this.dateRace.getValue() != null ? new java.util.Date(java.sql.Date.valueOf(this.dateRace.getValue()).getTime()) : null;
		Date endDateRegistration = this.endDateRegistration.getValue() != null ? new java.util.Date(java.sql.Date.valueOf(this.endDateRegistration.getValue()).getTime()) : null;
		
		Race race = new Race(idRace, name, place, dateRace, boatsNumber, registrationFee, endDateRegistration, StatusCode.ACTIVE);
		RequestType type = this.race == null ? RequestType.INSERT_RACE : RequestType.UPDATE_RACE;
		
		boolean result = this.app.getMessage(ClientHelper.getResponseType(new Request(type, race)));
		if (result)
			this.app.initRaces();
	}
	
	/**
	 * 
	 * @param id
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
        
        if (this.race == null) {
        	this.title.setText("ADD A NEW RACE");
        } else {
        	this.title.setText("UPDATE THE RACE WITH ID " + this.race.getId());
        }
    }
	
}
