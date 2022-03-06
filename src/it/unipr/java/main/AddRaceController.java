package it.unipr.java.main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;

/**
 * The class {@code AddRaceController} supports the insertion of a new race. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class AddRaceController {
	
	private App main;
	
	@FXML
    private TextField name, place, boatsNumber, registrationFee;
	
	@FXML
	private DatePicker dataRace;
	
	@FXML
    private HBox boxUser;
	
	@FXML
    private Button addButton;
	
	@FXML
    private void initialize() {	
		this.addButton.setOnMouseClicked(event -> {    		
    		this.addRace();
        });
    }
	
	/**
	 * Adds a boat owned by the logged in user or a club member chosen by an employee.
	**/
	public void addRace() {
		if (!this.name.getText().isEmpty() && !this.place.getText().isEmpty() && this.dataRace.getValue() != null && !this.boatsNumber.getText().isEmpty() && !this.registrationFee.getText().isEmpty()) {
			int boatsNumber = this.main.convertToInteger(this.boatsNumber.getText());	
			if (boatsNumber <= 0) {
				return;
			}
			
			float registrationFee = (float) this.main.convertToDouble(this.registrationFee.getText());
			if (registrationFee <= 0) {
				return;
			}
			
			java.sql.Date localDate = java.sql.Date.valueOf(this.dataRace.getValue());
			java.util.Date utilDate = new java.util.Date(localDate.getTime());
			
			System.out.println(utilDate);
			
			if (this.main.getClub().getRaceByDate(utilDate) != null) {
				this.main.showAlert(Alert.AlertType.WARNING, "Error", null,"Already exists a boat with entered name.");
			} else {
				System.out.println("ok");
    			/*this.main.getClub().insertBoat(name.getText(), length, owner);
				
				this.main.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The new boat has been added correctly.");
				this.main.initBoats();
				
			    Stage stage = (Stage) this.addButton.getScene().getWindow();
			    stage.close();
			*/}
		} else {
			this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
		}
	}
	
	/**
     * Sets the reference to the main application.
     * 
     * @param main the reference to the main.
    **/
    public void setMain(final App main) {
        this.main = main;
    }
	
}
