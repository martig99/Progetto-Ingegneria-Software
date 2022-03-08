package it.unipr.java.main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The class {@code UpsertRaceController} supports the insertion of update a race. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertRaceController {
	
	private App app;
	private Integer idRace;
	
	@FXML
    private TextField name, place, boatsNumber, registrationFee;
	
	@FXML
	private DatePicker dateRace;
	
	@FXML
    private HBox boxUser;
	
	@FXML
    private Button upsertButton;
	
	@FXML
    private void initialize() {	
		this.upsertButton.setOnMouseClicked(event -> {    		
    		this.upsertRace();
        });
    }
	
	
	
	
	/**
	 * Adds a boat owned by the logged in user or a club member chosen by an employee.
	**/
	public void upsertRace() {
		String name = null, place = null;
		Integer boatsNumber = null;
		Float registrationFee = null;
		if (this.idRace == null) {
			if (this.name.getText().isEmpty() || this.place.getText().isEmpty() || this.dateRace.getValue() == null || this.boatsNumber.getText().isEmpty() || this.registrationFee.getText().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error inserting new race.", null, "Please complete all fields.");
				return;
			}
		} else {
			if (this.name.getText().isEmpty() && this.place.getText().isEmpty() && this.dateRace.getValue() == null && this.boatsNumber.getText().isEmpty() && this.registrationFee.getText().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
				return;
			}
		}
		
		if (!this.boatsNumber.getText().isEmpty()) {
			boatsNumber = this.app.convertToInteger(this.boatsNumber.getText());	
			if (boatsNumber <= 0) {
				return;
			}
		}
		if (!this.registrationFee.getText().isEmpty()) {	
			registrationFee = (float) this.app.convertToDouble(this.registrationFee.getText());
			if (registrationFee <= 0) {
				return;
			}
		}
			
		if (!this.name.getText().isEmpty()) {
			name = this.name.getText();
		}
		
		if (!this.place.getText().isEmpty()) {
			place = this.place.getText();
		}
		java.util.Date utilDate = null;
		if(this.dateRace.getValue() != null) //da vedere domani per la storia della modifica dove la data può essere nulla 
		{
			java.sql.Date localDate = java.sql.Date.valueOf(this.dateRace.getValue());
			utilDate = new java.util.Date(localDate.getTime());
			//System.out.println(utilDate);
		}	
		if (utilDate != null && this.app.getClub().getIdRaceByDate(utilDate) != -1 && this.app.getClub().getIdRaceByDate(utilDate) != idRace && idRace != null) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null,"A race already exists on that day.");
		} else {
				String message = "";
				if(idRace == null)
				{
					this.app.getClub().insertRace(name,place,utilDate,boatsNumber,registrationFee);
					message = "The new race has been added correctly.";
				}
				else
				{
					this.app.getClub().updateRace(idRace,name,place,utilDate,boatsNumber,registrationFee);
					message = "The race has been updated correctly.";
				}
				this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, message);
				this.app.initRaces();
			    Stage stage = (Stage) this.upsertButton.getScene().getWindow();
			    stage.close();
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
     * Sets the reference to the main application.
     * 
     * @param main the reference to the main.
    **/
    public void setApp(final App app) {
        this.app = app;
    }
	
}
