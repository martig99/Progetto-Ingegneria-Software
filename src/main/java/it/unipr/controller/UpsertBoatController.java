package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * The class {@code UpsertBoatController} supports the section for the insertion or updating of a boat.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
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
	
	/**
	 * {@inheritDoc}
	**/
	@FXML
    private void initialize() {	
		this.upsertButton.setOnMouseClicked(event -> {
			if (this.boat == null)
				this.insertBoat();
			else
				this.updateBoat();
        });
		
		this.link.setOnMouseClicked(event -> {
			this.app.initBoats();
        });
	}

	/**
	 * Performs the insertion of a boat.
	**/
	public void insertBoat() {
		if (this.name.getText().trim().isEmpty() || this.length.getText().trim().isEmpty()) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
			return;
		}
		
		int length = this.app.convertToInteger(this.length.getText());			
		if (length <= 0) {
			return;
		}
		
		Boat boat = new Boat(0, this.name.getText(), length, null, StatusCode.ACTIVE);
		String emailMember = (this.app.getLoggedUser() instanceof Member) ? this.app.getLoggedUser().getEmail() : this.users.getSelectionModel().getSelectedItem().toString();

		ResponseType responseType = ClientHelper.getResponseType(new Request(RequestType.INSERT_BOAT, Arrays.asList(boat, emailMember)));
		boolean result = this.app.isSuccessfulMessage(responseType);
		if (result)
			this.app.initBoats();
	}
	
	/**
	 * Performs the updating of a boat.
	**/
	public void updateBoat() {
		if (this.name.getText().trim().isEmpty() && this.length.getText().trim().isEmpty()) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
			return;
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
		
		String name = !this.name.getText().isEmpty() ? this.name.getText() : null;
		Boat boat = new Boat(this.boat.getId(), name, length, this.boat.getOwner(), StatusCode.ACTIVE);

		ResponseType responseType = ClientHelper.getResponseType(new Request(RequestType.UPDATE_BOAT, Arrays.asList(boat)));
		boolean result = this.app.isSuccessfulMessage(responseType);
		if (result)
			this.app.initBoats();
	}
	
	/**
	 * Sets the boat to insert or update.
	 * 
	 * @param boat the boat.
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
        
        if (this.boat == null) {
    		this.title.setText("ADD A NEW BOAT");
    	} else {
    		this.title.setText("UPDATE THE BOAT WITH ID " + this.boat.getId());
    	}
        
        if (this.app.getLoggedUser() instanceof Employee) {
        	if (this.boat == null) {
        		this.app.setVisibleElement(this.users, true);
        	} else {
        		this.app.setVisibleElement(this.users, false);
        	}
        	
        	ObservableList<String> listUser = FXCollections.<String>observableArrayList();
    		listUser.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_MEMBERS_EMAIL), String.class));
    		this.users.setItems(listUser);
        }
    }
	
}
