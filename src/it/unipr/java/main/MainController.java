package it.unipr.java.main;

import java.util.ArrayList;

import it.unipr.java.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * The class {@code MainController} supports the menu and the different sections of the application.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class MainController {
	
	private App app;
	
	@FXML
	private HBox menu;
	
	@FXML
	private Text linkBoats, linkRaces, linkPayments, linkFees, linkUsers;
	
	@FXML
	private ImageView logout, notificationsImage;
	
	@FXML
	private StackPane notificationsBlock;
	
	@FXML
	private Label numberNotifications;

    @FXML
    private void initialize() {     	
    	this.linkBoats.setOnMouseClicked(clickEvent -> {
        	this.app.initBoats();
        	this.app.activeLinkMenu(this.menu, this.linkBoats);
        });
    	
    	this.linkRaces.setOnMouseClicked(clickEvent -> {
    		this.app.initRaces();
    		this.app.activeLinkMenu(this.menu, this.linkRaces);
        });
    	
    	this.linkPayments.setOnMouseClicked(clickEvent -> {
    		this.app.initPayments(FeeType.MEMBERSHIP);
    		this.app.activeLinkMenu(this.menu, this.linkPayments);
        });
    	
    	this.linkFees.setOnMouseClicked(clickEvent -> {
    		this.app.initFees();
    		this.app.activeLinkMenu(this.menu, this.linkFees);
        });
    	
    	this.linkUsers.setOnMouseClicked(clickEvent -> {
    		this.app.initUsers(UserType.MEMBER);
    		this.app.activeLinkMenu(this.menu, this.linkUsers);
        });
    	
    	this.logout.setOnMouseClicked(clickEvent -> {
        	this.app.initLogout();
        });
    	
    	this.notificationsImage.setOnMouseClicked(clickEvent -> {
    		this.app.activeLinkMenu(this.menu, null);
        	this.app.initNotifications();
        });
    }
    
    /**
     * 
     * @return
    **/
    public HBox getMenu() {
    	return this.menu;
    }
    
    /**
     * 
    **/
    public void updateNumberNotifications() {
    	ArrayList<Boat> listBoatsUnpaidStorageFee = this.app.getClub().getAllUnpaidStorageFeeByUser(this.app.getLoggedUser());
    	int num = listBoatsUnpaidStorageFee.size();
    	
    	if (!this.app.getClub().checkPaymentFee(this.app.getLoggedUser(), null, FeeType.MEMBERSHIP)) {
    		num++;
    	}
    	
		this.numberNotifications.setText(Integer.toString(num));
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
        	this.app.setVisibleElement(this.notificationsBlock, false);
        }	
        
        if (this.app.getLoggedUser() instanceof Member) {
			this.updateNumberNotifications();
			
    		if(!this.app.getClub().checkPaymentFee(this.app.getLoggedUser(), null, FeeType.MEMBERSHIP)) {
        		this.app.activeLinkMenu(this.menu, this.linkPayments);
        		this.app.toggleLinkMenu(this.menu, true);
        		this.app.initPayments(FeeType.MEMBERSHIP);
        		
        		this.app.showAlert(Alert.AlertType.INFORMATION, "Payments error.", null, "Unpaid membership fee.");       		
        		return;
        	} else {
        		this.app.toggleLinkMenu(this.menu, false);
        	}
		}
        
        this.app.activeLinkMenu(this.menu, this.linkBoats);
		this.app.initBoats();
    }
}
