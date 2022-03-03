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
 * The class {@code AddBoatController} supports the insertion of a new boat. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class AddBoatController {
	
	private App main;
	
	@FXML
    private TextField name, length;
	
	@FXML
    private HBox boxUser;
	
	@FXML
    private ChoiceBox<String> users;
		
	@FXML
    private Button addButton;
	
	@FXML
    private void initialize() {	
		this.addButton.setOnMouseClicked(event -> {    		
    		this.addBoat();
        });
    }
	
	/**
	 * Adds a boat owned by the logged in user or a club member chosen by an employee.
	**/
	public void addBoat() {
		if (!this.name.getText().isEmpty() && !this.length.getText().isEmpty()) {
			int length = this.main.convertToInteger(this.length.getText());			
			if (length <= 0) {
				return;
			}
			
			User owner = new User();
			if (this.main.getLoggedUser() instanceof Member) {
				owner = this.main.getLoggedUser(); 
			} else {
				String emailMember = this.users.getSelectionModel().getSelectedItem().toString();
				owner = this.main.getClub().getUserByEmail(emailMember);
				
				if (owner == null) {
					this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "Select the email of the owner of the boat.");
					return;
				}
			}
			
			if (this.main.getClub().getBoatByName(name.getText(), owner) != null) {
				this.main.showAlert(Alert.AlertType.WARNING, "Error", null,"Already exists a boat with entered name.");
			} else {
    			this.main.getClub().insertBoat(name.getText(), length, owner);
				
				this.main.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The new boat has been added correctly.");
				this.main.initBoats();
				
			    Stage stage = (Stage) this.addButton.getScene().getWindow();
			    stage.close();
			}
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
        
        if (this.main.getLoggedUser() instanceof Employee) {
        	this.boxUser.setVisible(true);
        	this.boxUser.setManaged(true);
        	
        	ObservableList<String> listUser = FXCollections.<String>observableArrayList();
			listUser.addAll(this.main.getClub().getAllMembersEmail());
			this.users.setItems(listUser);
        }
    }
	
}
