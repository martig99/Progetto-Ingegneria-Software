package it.unipr.java.main;

import java.util.Date;

import it.unipr.java.model.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

/**
 * The class {@code UpsertBoatRegisterController} supports registration of a boat at a race.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertRaceRegistrationController {
	
	private App app;
	private int idRace;
	private Integer idRegistration;
	
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
						this.setChoiceBoxBoats(this.app.getClub().getUserByEmail(newEmail));
					}
				}
			});

		this.saveButton.setOnMouseClicked(event -> {    		
    		this.upsertBoatRegistration();
        });
		
		this.link.setOnMouseClicked(event -> {  
			if (this.idRegistration == null) {
				this.app.initRaces();
			} else {
				this.app.initRaceRegistrations(this.idRace);
			}
        });
    }
	
	/**
	 * 
	**/
	public void upsertBoatRegistration() {
		User user = null;
		if (this.app.getLoggedUser() instanceof Member) {
			user = this.app.getLoggedUser();
		} else {
			if (this.idRegistration == null) {
				String emailMember = this.members.getSelectionModel().getSelectedItem().toString();
				user = this.app.getClub().getUserByEmail(emailMember);
	    		if (user == null) {
	    			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the club member.");
	    			return;
	    		}
			} else {
				RaceRegistration registration = this.app.getClub().getRaceRegistrationById(this.idRegistration);
				user = registration.getBoat().getOwner();
			}
		}
		
		if(!this.app.getClub().checkPaymentFee(user, null, FeeType.MEMBERSHIP)) {
    		this.app.showAlert(Alert.AlertType.INFORMATION, "Payments error.", "Cannot register the boat at this race.", "Unpaid membership fee.");
    		return;
		}
		
		Boat boat = null;
		String nameBoat = this.boats.getSelectionModel().getSelectedItem().toString();
		boat = this.app.getClub().getBoatByName(nameBoat, user);
		if (boat == null) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the boat.");
			return;
		}
		
		String message = "";
		if (this.idRegistration == null) {
			String descriptionPaymentService = this.paymentServices.getSelectionModel().getSelectedItem().toString();
			PaymentService paymentService = this.app.getClub().getPaymentServiceByDescription(descriptionPaymentService);
			if (paymentService == null) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the payment service.");
				return;
			}
			
			Race race = this.app.getClub().getRaceById(this.idRace);
			Date lastPayment = this.app.getClub().getLastPaymentFee(user, boat, FeeType.STORAGE);
			if (!this.app.getClub().checkPaymentFee(user, boat, FeeType.STORAGE) || lastPayment.before(race.getDate()) || lastPayment.equals(race.getDate())) {
				this.app.showAlert(Alert.AlertType.INFORMATION, "Payments error.", "Cannot register the boat at this race.",  "Unpaid storage fee for this boat.");
				return;
			}
			
			if (this.app.getClub().getBoatInRaceByMember(user, race) != null) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Only one boat per club member can be registered for a race.");
				return;
			} 
			
			this.app.getClub().registerBoatAtRace(race, user, boat, paymentService);
			message = "The boat " + nameBoat + " was correctly registered at the race.";
		} else {
			this.app.getClub().updateBoatRegistration(this.idRegistration, boat);
			message = "The boat has been updated correctly.";
		}
		
		this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, message);	
		this.app.initRaceRegistrations(this.idRace);
	}
	
	/**
	 * 
	**/
	public void setChoiceBoxMembers() {
    	ObservableList<String> listUser = FXCollections.<String>observableArrayList();
		listUser.addAll(this.app.getClub().getAllMembersEmail());
		this.members.setItems(listUser);
	}
	
	/**
	 * 
	**/
	public void setChoiceBoxBoats(final User owner) {
		ObservableList<String> listBoat = FXCollections.<String>observableArrayList();
    	listBoat.addAll(this.app.getClub().getAllNameBoatsByOwner(owner));
		this.boats.setItems(listBoat);
	}
	
	/**
	 * 
	**/
	public void setChoiceBoxPaymentServices() {
		ObservableList<String> listPaymentService = FXCollections.<String>observableArrayList();
        listPaymentService.addAll(this.app.getClub().getAllPaymentServicesDescription());
		this.paymentServices.setItems(listPaymentService);
	}
	
	/**
	 * 
	 * @param idRace
	**/
	public void setIdRace(final int idRace) {
		this.idRace = idRace;
	}
	
	/**
	 * 
	 * @param idRegistration
	**/
	public void setIdRegistration(final Integer idRegistration) {
		this.idRegistration = idRegistration;
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        Race race = this.app.getClub().getRaceById(this.idRace);
        if (this.idRegistration == null) {
        	this.title.setText("REGISTER A BOAT FOR THE RACE OF " + race.getDate());

        	if (this.app.getLoggedUser() instanceof Employee) {
            	this.app.setVisibleElement(this.members, true);
            	this.setChoiceBoxMembers();
            	
            	this.boats.setDisable(true);
            } else {
        		this.setChoiceBoxBoats(this.app.getLoggedUser());
        	}
        	
        	this.setChoiceBoxPaymentServices();
        } else {
        	this.title.setText("UPDATE THE REGISTRATION OF THE BOAT");
        	
        	this.app.setVisibleElement(this.members, false);
        	this.app.setVisibleElement(this.paymentServices, false);
        	
        	RaceRegistration registration = this.app.getClub().getRaceRegistrationById(this.idRegistration);
        	this.setChoiceBoxBoats(registration.getBoat().getOwner());
        }
    }
}
