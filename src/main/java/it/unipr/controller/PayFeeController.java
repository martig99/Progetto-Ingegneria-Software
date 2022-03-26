package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.Request;
import main.java.it.unipr.message.RequestType;
import main.java.it.unipr.model.*;

import javafx.fxml.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

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
					this.setBoats(ClientHelper.getObjectResponse(new Request(RequestType.GET_USER_BY_EMAIL, newEmail), User.class));
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
			user = ClientHelper.getObjectResponse(new Request(RequestType.GET_USER_BY_EMAIL, emailMember), User.class);
			if (user == null) {
    			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the user.");
    			return;
    		}
		}
				
		Boat boat = null;
		if (this.feeType == FeeType.STORAGE) {
			String nameBoat = this.boats.getSelectionModel().getSelectedItem().toString();
			boat = ClientHelper.getObjectResponse(new Request(RequestType.GET_BOAT_BY_NAME, nameBoat, user), Boat.class);

			if (boat == null) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the boat.");
				return;
			}
		}
		
		String descriptionPaymentService = this.paymentServices.getSelectionModel().getSelectedItem().toString();
		PaymentService paymentService = ClientHelper.getObjectResponse(new Request(RequestType.GET_PAYMENT_SERVICE_BY_DESCRIPTION, descriptionPaymentService), PaymentService.class);		
		if (paymentService == null) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please select the payment service.");
			return;
		}
				
		Payment payment = new Payment();
		
		payment.setMember(new Member(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getEmail()));
		payment.setBoat(boat);
		payment.setPaymentService(paymentService);
		payment.setFee(ClientHelper.getObjectResponse(new Request(RequestType.GET_FEE_BY_TYPE, this.feeType), Fee.class));
		
		this.app.getMessage(ClientHelper.getResponseType(new Request(RequestType.PAY_FEE, payment)));		
		this.app.initPayments(this.feeType);
			
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
        listBoat.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_NAME_BOATS_BY_OWNER, owner), String.class));
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
        	this.setMembers();
        }
        
        if (this.feeType == FeeType.STORAGE) {
        	this.title.setText("PAY STORAGE FEE");
        	this.app.setVisibleElement(this.boats, true);
        	
        	if (this.app.getLoggedUser() instanceof Employee) {
        		this.boats.setDisable(true);
        	} else {
        		this.setBoats(this.app.getLoggedUser());
        	}
        }
        
        this.setPaymentServices();
    }
}
