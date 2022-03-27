package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.fxml.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * The class {@code UpsertBoatRegisterController} supports registration of a boat at a race.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertRaceRegistrationController {
	
	private App app;
	private Race race;
	private RaceRegistration registration;
	
	@FXML
    private ChoiceBox<String> boats, members, paymentServices;
		
	@FXML
    private Button saveButton;
	
	@FXML
	private Text title, link;
	
	@FXML
    private void initialize() {	
		this.members.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends String> observable, String oldEmail, String newEmail) -> {
				if (this.app.getLoggedUser() instanceof Employee) {
					if (oldEmail != newEmail) {
						this.boats.setValue("Boat");
					}
					
					if (newEmail != null) {
						this.boats.setDisable(false);
						this.setBoats(ClientHelper.getObjectResponse(new Request(RequestType.GET_USER_BY_EMAIL, newEmail, null), User.class));
					}
				}
			});

		this.saveButton.setOnMouseClicked(event -> {    		
    		this.upsertRaceRegistration();
        });
		
		this.link.setOnMouseClicked(event -> {  
			if (this.registration == null) {
				this.app.initRaces();
			} else {
				this.app.initRaceRegistrations(this.race);
			}
        });
    }
	
	/**
	 * 
	**/
	public void upsertRaceRegistration() {
		User user = null;
		if (this.app.getLoggedUser() instanceof Member) { 
			user = (User) this.app.getLoggedUser();
		} else {
			if (this.registration == null) {
				String emailMember = this.members.getSelectionModel().getSelectedItem().toString();
				user = (User) ClientHelper.getObjectResponse(new Request(RequestType.GET_USER_BY_EMAIL, emailMember, null), User.class);
	    		if (user == null) {
	    			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the club member.");
	    			return;
	    		}
			} else {
				user = (User) this.registration.getBoat().getOwner();
			}
		}
				
		Boat boat = null;
		String nameBoat = this.boats.getSelectionModel().getSelectedItem().toString();
		boat = ClientHelper.getObjectResponse(new Request(RequestType.GET_BOAT_BY_NAME, nameBoat, user), Boat.class);
		if (boat == null) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the boat.");
			return;
		}
		
		int idRegistration = this.registration == null ? 0 : this.registration.getId();
		
		PaymentService paymentService = null;
		if (this.registration == null) {
			String descriptionPaymentService = this.paymentServices.getSelectionModel().getSelectedItem().toString();
			paymentService = ClientHelper.getObjectResponse(new Request(RequestType.GET_PAYMENT_SERVICE_BY_DESCRIPTION, descriptionPaymentService, null), PaymentService.class);
			if (paymentService == null) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the payment service.");
				return;
			}
		}
		
		RaceRegistration newRegistration = new RaceRegistration(idRegistration, new Date(), this.race, boat, StatusCode.ACTIVE);
		RequestType type = this.registration == null ? RequestType.INSERT_RACE_REGISTRATION : RequestType.UPDATE_RACE_REGISTRATION;

		boolean result = this.app.getMessage(ClientHelper.getResponseType(new Request(type, newRegistration, paymentService)));
		if (result)
			this.app.initRaceRegistrations(this.race);
	}
	
	/**
	 * 
	**/
	public void setMembers() {
    	ObservableList<String> listUser = FXCollections.<String>observableArrayList();
		listUser.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_MEMBERS_EMAIL), String.class));
		this.members.setItems(listUser);
	}
	
	/**
	 * 
	**/
	public void setBoats(final User owner) {
		ObservableList<String> listBoat = FXCollections.<String>observableArrayList();
    	listBoat.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_NAME_BOATS_BY_OWNER, owner, null), String.class));
		this.boats.setItems(listBoat);
	}
	
	/**
	 * 
	**/
	public void setPaymentServices() {
		ObservableList<String> listPaymentService = FXCollections.<String>observableArrayList();
        listPaymentService.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_PAYMENT_SERVICES_DESCRIPTION), String.class));
		this.paymentServices.setItems(listPaymentService);
	}
	
	/**
	 * 
	 * @param idRace
	**/
	public void setRace(final Race race) {
		this.race = race;
	}
	
	/**
	 * 
	 * @param idRegistration
	**/
	public void setRegistration(final RaceRegistration registration) {
		this.registration = registration;
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (this.registration == null) {
        	this.title.setText("REGISTER A BOAT FOR THE RACE OF " + this.app.setDateFormat(this.race.getDate()));

        	if (this.app.getLoggedUser() instanceof Employee) {
            	this.app.setVisibleElement(this.members, true);
            	this.setMembers();
            	
            	this.boats.setDisable(true);
            } else {
        		this.setBoats(this.app.getLoggedUser());
        	}
        	
        	this.setPaymentServices();
        } else {
        	this.title.setText("UPDATE THE REGISTRATION OF THE BOAT");
        	
        	this.app.setVisibleElement(this.members, false);
        	this.app.setVisibleElement(this.paymentServices, false);
        	
        	this.setBoats(this.registration.getBoat().getOwner());
        }
    }
}
