package it.unipr.java.main;

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
 * The class {@code PayFeeController} supports the payment of a fee.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class PayFeeController {
	
	private App app;
	private FeeType feeType;
	
	@FXML
	private Text title, link;
	
	@FXML
    private ChoiceBox<String> members, boats, paymentServices;
		
	@FXML
    private Button payButton;
	
	@FXML
    private void initialize() {	
		this.members.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends String> observable, String oldEmail, String newEmail) -> {
				if (this.feeType == FeeType.STORAGE && newEmail != null) {
					this.boats.setDisable(false);
					this.setChoiceBoxBoats(this.app.getClub().getUserByEmail(newEmail));
				}
			});

		this.payButton.setOnMouseClicked(event -> {    		
    		this.payFee();
        });
		
		this.link.setOnMouseClicked(clickEvent -> {
        	this.app.initPayments(this.feeType);
	    });
    }
	
	/**
	 * 
	**/
	public void payFee() {		
		User user = null;
		if (this.app.getLoggedUser() instanceof Member) {
			user = this.app.getLoggedUser();
		} else {
			String emailMember = this.members.getSelectionModel().getSelectedItem().toString();
			user = this.app.getClub().getUserByEmail(emailMember);
    		if (user == null) {
    			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the user who has to pay the membership fee.");
    			return;
    		}
		}
		
		Boat boat = null;
		if (this.feeType == FeeType.STORAGE) {
			String nameBoat = this.boats.getSelectionModel().getSelectedItem().toString();
			boat = this.app.getClub().getBoatByName(nameBoat, user);
			if (boat == null) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the boat.");
				return;
			}
		}
		
		String descriptionPaymentService = this.paymentServices.getSelectionModel().getSelectedItem().toString();
		PaymentService paymentService = this.app.getClub().getPaymentServiceByDescription(descriptionPaymentService);
		if (paymentService != null) {
			this.app.getClub().payFee(user, boat, null, this.feeType, paymentService, false);
			this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The membership fee has been paid correctly.");
			
			if (this.app.getClub().checkPaymentFee(user, null, FeeType.MEMBERSHIP)) {
				if (this.app.getMainController() != null) {
					this.app.toggleLinkMenu(this.app.getMainController().getMenu(), false);
				}
			}
			
			this.app.initPayments(this.feeType);
		} else {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the payment service.");
			return;
		}
			
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
	 * @param type
	**/
	public void setFeeType(final FeeType type) {
		this.feeType = type;
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (this.app.getLoggedUser() instanceof Employee) {
        	this.title.setText("PAY MEMBERSHIP FEE");

        	this.app.setVisibleElement(this.members, true);
        	this.setChoiceBoxMembers();
        }
        
        if (this.feeType == FeeType.STORAGE) {
        	this.title.setText("PAY STORAGE FEE");
        	this.app.setVisibleElement(this.boats, true);
        	
        	if (this.app.getLoggedUser() instanceof Employee) {
        		this.boats.setDisable(true);
        	} else {
        		this.setChoiceBoxBoats(this.app.getLoggedUser());
        	}
        }
        
        this.setChoiceBoxPaymentServices();
    }
}
