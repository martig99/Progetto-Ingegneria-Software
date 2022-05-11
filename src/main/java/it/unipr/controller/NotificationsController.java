package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * The class {@code NotificationsController} supports the display of all notifications. 
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class NotificationsController {

	private App app;

	@FXML
	private Label membershipFee, storageFees, noNotifications;
	
	@FXML
	private HBox containerMembershipFee, containerStorageFees;
    
    @FXML
    private void initialize() {}
    
    /**
     * Sets the list of notifications.
    **/
    public void setNotifications() {
    	String textStorageFees = "", textMembershipFee = "";
    	
		ArrayList<Notification> list = ClientHelper.getListResponse(new Request(RequestType.GET_ALL_NOTIFICATIONS, Arrays.asList(this.app.getLoggedUser())), Notification.class);
        if (list.size() > 0) {
        	for (Notification notification: list) {
        		if (notification.getFee().getType() == FeeType.MEMBERSHIP) {
        			textMembershipFee = notification.getFee().getAmount() + "€";
        		} else {
        			textStorageFees += notification.getBoat().getName() + " - " + notification.getBoat().getLength() * notification.getFee().getAmount() + "€\n";
        		}
        	}
        	
			this.membershipFee.setText(textMembershipFee);
        	this.storageFees.setText(textStorageFees);
        } else {
        	this.app.setVisibleElement(this.containerMembershipFee, false);
        	this.app.setVisibleElement(this.containerStorageFees, false);
        	this.app.setVisibleElement(this.noNotifications, true);
        }
    }
    
    /**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;        

        this.setNotifications();
    }
}
