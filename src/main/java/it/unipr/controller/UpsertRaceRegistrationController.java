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
 * The class {@code UpsertBoatRegisterController} supports the section for the insertion or updating of a race registration.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
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
	
	/**
	 * {@inheritDoc}
	**/
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
						this.setBoats(ClientHelper.getObjectResponse(new Request(RequestType.GET_USER_BY_EMAIL, Arrays.asList(newEmail, UserType.MEMBER)), Member.class));
					}
				}
			});

		this.saveButton.setOnMouseClicked(event -> {    	
			if (this.registration == null) {
				this.insertRaceRegistration();
			} else {
				this.updateRaceRegistration();
			}
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
	 * Performs the insertion of the race registration.
	**/
	public void insertRaceRegistration() {
		String emailMember = (this.app.getLoggedUser() instanceof Member) ? this.app.getLoggedUser().getEmail() : this.members.getSelectionModel().getSelectedItem().toString();
		String nameBoat = this.boats.getSelectionModel().getSelectedItem().toString();
		String descriptionPaymentService = this.paymentServices.getSelectionModel().getSelectedItem().toString();

		boolean result = this.app.isSuccessfulMessage(ClientHelper.getResponseType(new Request(RequestType.INSERT_RACE_REGISTRATION, Arrays.asList(emailMember, nameBoat, descriptionPaymentService, this.race))));
		if (result)
			this.app.initRaceRegistrations(this.race);
	}
	
	/**
	 * Performs the updating of the race registration.
	**/
	public void updateRaceRegistration() {
		String emailMember;
		if (this.app.getLoggedUser() instanceof Member) { 
			emailMember = this.app.getLoggedUser().getEmail();
		} else {
			emailMember = this.registration.getBoat().getOwner().getEmail();
		}
		
		String nameBoat = this.boats.getSelectionModel().getSelectedItem().toString();
		String descriptionPaymentService = this.paymentServices.getSelectionModel().getSelectedItem().toString();

		boolean result = this.app.isSuccessfulMessage(ClientHelper.getResponseType(new Request(RequestType.UPDATE_RACE_REGISTRATION, Arrays.asList(this.registration.getId(), emailMember, nameBoat, descriptionPaymentService, this.race))));
		if (result)
			this.app.initRaceRegistrations(this.race);
	}
	
	
	/**
	 * Sets the email address of all users in the list.
	**/
	public void setMembers() {
    	ObservableList<String> listUser = FXCollections.<String>observableArrayList();
		listUser.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_MEMBERS_EMAIL), String.class));
		this.members.setItems(listUser);
	}
	
	/**
	 * Sets the name of all the boats that belong to the logged in user or to a user selected from the list of members.
	 * 
	 * @param owner the owner of the boats. 
	**/
	public void setBoats(final Member owner) {
		ObservableList<String> listBoat = FXCollections.<String>observableArrayList();		
    	listBoat.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_NAME_BOATS_BY_OWNER, Arrays.asList(owner)), String.class));
		this.boats.setItems(listBoat);
	}
	
	/**
	 * Sets the description of all payment services in the list.
	**/
	public void setPaymentServices() {
		ObservableList<String> listPaymentService = FXCollections.<String>observableArrayList();
        listPaymentService.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_PAYMENT_SERVICES_DESCRIPTION), String.class));
		this.paymentServices.setItems(listPaymentService);
	}
	
	/**
	 * Sets the race registration to insert or update.
	 * 
	 * @param registration the race registration.
	**/
	public void setRegistration(final RaceRegistration registration) {
		this.registration = registration;
	}
	
	/**
	 * Sets the race to which the registration refer.
	 * 
	 * @param race the race.
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
        
        Member member = new Member();
        if (this.registration == null) {
        	this.title.setText("REGISTER A BOAT FOR THE RACE OF " + this.app.setDateFormat(this.race.getDate()));

        	if (this.app.getLoggedUser() instanceof Employee) {
            	this.app.setVisibleElement(this.members, true);
            	this.setMembers();
            	
            	this.boats.setDisable(true);
            } else {
            	member = (Member) this.app.getLoggedUser();
        	}
        } else {
        	this.title.setText("UPDATE THE REGISTRATION OF THE BOAT");
        	
        	this.app.setVisibleElement(this.members, false);
        	member = this.registration.getBoat().getOwner();
        }
        
        this.setBoats(member);
        this.setPaymentServices();
    }
}
