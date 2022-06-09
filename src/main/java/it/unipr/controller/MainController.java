package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.model.*;

import javafx.fxml.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * The class {@code MainController} supports the menu and the notification counter.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class MainController {
	
	private App app;
	
	@FXML
	private HBox menu;
	
	@FXML
	private Text linkBoats, linkRaces, linkPayments, linkFees, linkUsers;
	
	@FXML
	private ImageView logout, notificationsImage;

	/**
	 * {@inheritDoc} 
	**/
    @FXML
    private void initialize() {
    	this.setMenu();
    }
    
    /**
     * Set the links of the main menu.
    **/
    public void setMenu() {
    	this.linkBoats.setOnMouseClicked(clickEvent -> {
        	this.app.initBoats();
        	this.app.activateLinkMenu(this.menu, this.linkBoats);
        });
    	
    	this.linkRaces.setOnMouseClicked(clickEvent -> {
    		this.app.initRaces();
    		this.app.activateLinkMenu(this.menu, this.linkRaces);
        });
    	
    	this.linkPayments.setOnMouseClicked(clickEvent -> {
    		this.app.initPayments(FeeType.MEMBERSHIP);
    		this.app.activateLinkMenu(this.menu, this.linkPayments);
        });
    	
    	this.linkFees.setOnMouseClicked(clickEvent -> {
    		this.app.initFees();
    		this.app.activateLinkMenu(this.menu, this.linkFees);
        });
    	
    	this.linkUsers.setOnMouseClicked(clickEvent -> {
    		this.app.initUsers(UserType.MEMBER);
    		this.app.activateLinkMenu(this.menu, this.linkUsers);
        });

    	this.notificationsImage.setOnMouseClicked(clickEvent -> {
        	this.app.initNotifications();
    		this.app.activateLinkMenu(this.menu, null);
        });
    	
    	this.logout.setOnMouseClicked(clickEvent -> {
    		this.app.initLogout();
        });
    }
    
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        this.menu.setVisible(true);
    	
        if (this.app.getLoggedUser() instanceof Employee) { 
        	Employee employee = (Employee) this.app.getLoggedUser();
        	if (employee.isAdministrator()) {
        		this.app.setVisibleElement(this.linkFees, true);
        	}
        	
        	this.app.setVisibleElement(this.linkUsers, true);
        	this.app.setVisibleElement(this.notificationsImage, false);
        }
        
        this.app.activateLinkMenu(this.menu, this.linkBoats);
		this.app.initBoats();
    }
}
