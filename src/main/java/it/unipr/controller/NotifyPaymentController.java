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
 * The class {@code NotifyPaymentController} supports the section to notify a payment.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class NotifyPaymentController {
	
	private App app;
	private FeeType feeType;
	
	@FXML
	private Text title, link;
	
	@FXML
    private ChoiceBox<String> members, boats;
		
	@FXML
    private Button sendButton;
	
	/**
	 * {@inheritDoc}
	**/
	@FXML
    private void initialize() {	
		this.members.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends String> observable, String oldEmail, String newEmail) -> {
				if (this.feeType == FeeType.STORAGE && newEmail != null) {
					this.boats.setDisable(false);					
					this.setBoats(ClientHelper.getObjectResponse(new Request(RequestType.GET_USER_BY_EMAIL, Arrays.asList(newEmail, UserType.MEMBER)), Member.class));
				}
			});

		this.sendButton.setOnMouseClicked(event -> {    		
    		this.notifyPayment();
        });
		
		this.link.setOnMouseClicked(clickEvent -> {
        	this.app.initPayments(this.feeType);
	    });
    }
	
	/**
	 * Performs the creation of the payment notification for a certain fee.
	**/
	public void notifyPayment() {		
		String emailMember;
		if (this.app.getLoggedUser() instanceof Member) {
			emailMember = this.app.getLoggedUser().getEmail();
		} else {
			emailMember = this.members.getSelectionModel().getSelectedItem().toString();
		}
				
		String nameBoat = this.feeType == FeeType.STORAGE ? this.boats.getSelectionModel().getSelectedItem().toString() : null;

		boolean result = this.app.isSuccessfulMessage(ClientHelper.getResponseType(new Request(RequestType.NOTIFY_PAYMENT, Arrays.asList(emailMember, this.feeType, nameBoat))));		
		if (result)
			this.app.initPayments(this.feeType);
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
	**/
	public void setBoats(final User owner) {
		ObservableList<String> listBoat = FXCollections.<String>observableArrayList();	
        listBoat.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_NAME_BOATS_BY_OWNER, Arrays.asList(owner)), String.class));
		this.boats.setItems(listBoat);
	}

	/**
	 * Sets the type of fee of which the employee wants to notify the payment.
	 * 
	 * @param type the type of fee.
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

        this.setMembers();
        
        if (this.feeType == FeeType.STORAGE) {
        	this.title.setText("NOTIFY THE PAYMENT OF THE STORAGE FEE");
        	this.app.setVisibleElement(this.boats, true);
        	
        	this.boats.setDisable(true);
        } else if (this.feeType == FeeType.MEMBERSHIP) {
        	this.title.setText("NOTIFY THE PAYMENT OF THE MEMBERSHIP FEE");
        	
        	this.boats.setDisable(false);
        }
    }
}
