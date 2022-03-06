package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The class {@code UpsertBoatController} supports the update or insert of a boat. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertBoatController {
	
	private App app;
	private Integer idBoat;
	
	@FXML
    private TextField name, length;
	
	@FXML
    private HBox boxUser;
	
	@FXML
    private ChoiceBox<String> users;

	@FXML
    private Button upsertButton;
	
	@FXML
    private void initialize() {	
		this.upsertButton.setOnMouseClicked(event -> {
			this.upsertBoat();
        });
	}

	/**
	 * 
	**/
	public void upsertBoat() {
		String name = null;
		Integer length = null;
		
		if (this.idBoat == null) {
			if (this.name.getText().isEmpty() || this.length.getText().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error inserting new boat.", null, "Please complete all fields.");
				return;
			}
		} else {
			if (this.name.getText().isEmpty() && this.length.getText().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
				return;
			}
		}
			
		if (!this.length.getText().isEmpty()) {
			length = this.app.convertToInteger(this.length.getText());			
			if (length <= 0) {
				return;
			}
		}
		
		if (!this.name.getText().isEmpty()) {
			name = this.name.getText();
		}
			
		User owner = new User();
		if (this.app.getLoggedUser() instanceof Member) {
			owner = this.app.getLoggedUser(); 
		} else {
			if (this.idBoat == null) {
				String emailMember = this.users.getSelectionModel().getSelectedItem().toString();
				owner = this.app.getClub().getUserByEmail(emailMember);
				
				if (owner == null) {
					this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Select the email of the owner of the boat.");
					return;
				}
			} else {
				Boat boat = this.app.getClub().getBoatById(this.idBoat);
				owner = boat.getOwner();
			}
		}
		
		if (this.app.getClub().getBoatByName(name, owner) != null) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null,"Already exists a boat with entered name.");
			return;
		} else {				
			String message = "";
			if (this.idBoat == null) {
				this.app.getClub().insertBoat(name, length, owner);
				message = "The new boat has been added correctly.";
			} else {
				this.app.getClub().updateBoat(this.idBoat, name, length);
				message = "The boat has been updated correctly.";
			}
			
			this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, message);
			this.app.initBoats();
			
		    Stage stage = (Stage) this.upsertButton.getScene().getWindow();
		    stage.close();
		}
	}
	
	/**
	 * 
	 * @param id
	**/
	public void setIdBoat(final Integer idBoat) {
		this.idBoat = idBoat;
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (this.app.getLoggedUser() instanceof Employee) {
        	if (this.idBoat == null) {
        		this.app.setVisibleElement(this.boxUser, true);
        	} else {
        		this.app.setVisibleElement(this.boxUser, false);
        	}
        	
        	ObservableList<String> listUser = FXCollections.<String>observableArrayList();
			listUser.addAll(this.app.getClub().getAllMembersEmail());
			this.users.setItems(listUser);
        }
    }
	
}
