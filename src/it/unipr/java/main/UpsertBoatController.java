package it.unipr.java.main;

import it.unipr.java.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
	private Text title, link;
	
	@FXML
    private TextField name, length;
	
	@FXML
    private ChoiceBox<String> users;

	@FXML
    private Button upsertButton;
	
	@FXML
    private void initialize() {	
		this.upsertButton.setOnMouseClicked(event -> {
			this.upsertBoat();
        });
		
		this.link.setOnMouseClicked(event -> {
			this.app.initBoats();
        });
	}

	/**
	 * 
	**/
	public void upsertBoat() {		
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
		
		Integer length = null;
		if (!this.length.getText().isEmpty()) {
			length = this.app.convertToInteger(this.length.getText());			
			if (length <= 0) {
				return;
			}
		}
		
		String name = !this.name.getText().isEmpty() ? this.name.getText() : null;
			
		User user = new User();
		Boat boat = new Boat();
		if (this.app.getLoggedUser() instanceof Member) {
			user = this.app.getLoggedUser(); 
		} else {
			if (this.idBoat == null) {
				String emailMember = this.users.getSelectionModel().getSelectedItem().toString();
				user = this.app.getClub().getUserByEmail(emailMember);
				
				if (user == null) {
					this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Select the email of the owner of the boat.");
					return;
				}
			} else {
				boat = this.app.getClub().getBoatById(this.idBoat);
				user = boat.getOwner();
			}
		}
		
		if(!this.app.getClub().checkPaymentFee(user, null, FeeType.MEMBERSHIP)) {
    		this.app.showAlert(Alert.AlertType.INFORMATION, "Payments error.", "Cannot save changes of this boat.", "Unpaid membership fee.");
    		return;
		}
		
		if (this.app.getClub().getBoatByName(name, user) != null) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null,"Already exists a boat with entered name.");
			return;
		} 	
		
		String message = "";
		if (this.idBoat == null) {
			this.app.getClub().insertBoat(name, length, user);
			message = "The new boat has been added correctly.";
		} else {
			this.app.getClub().updateBoat(this.idBoat, name, length);
			message = "The boat has been updated correctly.";
		}
		
		this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, message);
		this.app.initBoats();
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
        		this.app.setVisibleElement(this.users, true);
        		this.title.setText("ADD A NEW BOAT");
        	} else {
        		this.app.setVisibleElement(this.users, false);
        		this.title.setText("UPDATE THE BOAT WITH ID " + this.idBoat);
        	}
        	
        	ObservableList<String> listUser = FXCollections.<String>observableArrayList();
			listUser.addAll(this.app.getClub().getAllMembersEmail());
			this.users.setItems(listUser);
        }
    }
	
}
