package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * The class {@code UpsertBoatController} supports the update or insert of a boat. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertBoatController {
	
	private App app;	
	private Boat boat;
	
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
		if (this.boat == null) {
			if (this.name.getText().trim().isEmpty() || this.length.getText().trim().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
				return;
			}
		} else {
			if (this.name.getText().trim().isEmpty() && this.length.getText().trim().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
				return;
			}
		}

		int length = 0;
		if (!this.length.getText().isEmpty()) {
			length = this.app.convertToInteger(this.length.getText());			
			if (length <= 0) {
				return;
			}
		} else {
			length = this.boat.getLength();
		}
		
		int idBoat = this.boat == null ? 0 : this.boat.getId();
		String name = !this.name.getText().isEmpty() ? this.name.getText() : null;
			
		User user = new User();
		if (this.app.getLoggedUser() instanceof Member) {
			user = this.app.getLoggedUser(); 
		} else {
			if (this.boat == null) {
				String emailMember = this.users.getSelectionModel().getSelectedItem().toString();
				user = ClientHelper.getObjectResponse(new Request(RequestType.GET_USER_BY_EMAIL, emailMember, null), User.class);
				
				if (user == null) {
					this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the user.");
	    			return;
				}
			}
		}
		
		RequestType type = this.boat == null ? RequestType.INSERT_BOAT : RequestType.UPDATE_BOAT;	
		Member member = new Member(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
		Request request = new Request(type, new Boat(idBoat, name, length, member, StatusCode.ACTIVE), null);
		
		boolean result = this.app.getMessage(ClientHelper.getResponseType(request));
		if (result)
			this.app.initBoats();
	}
	
	/**
	 * 
	 * @param id
	**/
	public void setBoat(final Boat boat) {
		this.boat = boat;
	}

	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (this.app.getLoggedUser() instanceof Employee) {
        	if (this.boat == null) {
        		this.app.setVisibleElement(this.users, true);
        		this.title.setText("ADD A NEW BOAT");
        	} else {
        		this.app.setVisibleElement(this.users, false);
        		this.title.setText("UPDATE THE BOAT WITH ID " + this.boat.getId());
        	}
        	
        	ObservableList<String> listUser = FXCollections.<String>observableArrayList();
    		listUser.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_MEMBERS_EMAIL), String.class));
    		this.users.setItems(listUser);
        }
    }
	
}
