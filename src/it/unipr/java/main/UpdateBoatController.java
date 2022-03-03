package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The class {@code UpdateBoatController} supports the update of a new boat. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpdateBoatController {
	
	private App main;
	private int id;
	
	@FXML
    private TextField name, length;

	@FXML
    private Button updateButton;
	
	@FXML
    private void initialize() {	
		this.updateButton.setOnMouseClicked(event -> {    		
    		this.updateBoat();
        });
    }

	public void updateBoat() {
		if (!this.name.getText().isEmpty() || !this.length.getText().isEmpty()) {
			Integer length = null;
			if (!this.length.getText().isEmpty()) {
				length = this.main.convertToInteger(this.length.getText());			
				if (length <= 0) {
					return;
				}
			}
			
			User owner = new User();
			if (this.main.getLoggedUser() instanceof Member) {
				owner = this.main.getLoggedUser(); 
			} else {
				owner = this.main.getClub().getUserById(id);
			}
			
			if (this.main.getClub().getBoatByName(name.getText(), owner) != null) {
				this.main.showAlert(Alert.AlertType.WARNING, "Error", null,"Already exists a boat with entered name.");
			} else {
				String name = null;
				if (!this.name.getText().isEmpty()) {
					name = this.name.getText();
				}
				
				this.main.getClub().updateBoat(id, name, length);
				this.main.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The boat has been updated correctly.");
				this.main.initBoats();
				
			    Stage stage = (Stage) this.updateButton.getScene().getWindow();
			    stage.close();
			}
		} else {
			this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
		}
	}
	
	public void setIdBoat(final int id) {
		this.id = id;
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
