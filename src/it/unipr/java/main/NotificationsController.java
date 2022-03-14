package it.unipr.java.main;

import it.unipr.java.model.*;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * The class {@code NotificationsController} supports the display of all notifications. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class NotificationsController {

	private App app;

	@FXML
	private Label membershipFee, storageFees;
	
	@FXML
	private HBox containerMembershipFee, containerStorageFees;
    
    @FXML
    private void initialize() {	

    }
    
    /**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (!this.app.getClub().checkPaymentFee(this.app.getLoggedUser(), null, FeeType.MEMBERSHIP)) {
        	this.membershipFee.setText(this.app.getClub().getFee(FeeType.MEMBERSHIP).getAmount() + "€");        	
    	} else {
    		this.app.setVisibleElement(this.containerMembershipFee, false);
    	}
        
    	String textStorageFees = "";
    	ArrayList<Boat> listBoatsUnpaidStorageFee = this.app.getClub().getAllUnpaidStorageFeeByUser(this.app.getLoggedUser());
    	if (listBoatsUnpaidStorageFee.size() > 0) {
	    	for (Boat boat: listBoatsUnpaidStorageFee) {
	    		textStorageFees += boat.getName() + " - " + boat.getLength() * this.app.getClub().getFee(FeeType.STORAGE).getAmount() + "€\n";
	    	}
	    	
	    	this.storageFees.setText(textStorageFees);
    	} else {
    		this.app.setVisibleElement(this.containerStorageFees, false);
    	}
    	
    	
    }
}
