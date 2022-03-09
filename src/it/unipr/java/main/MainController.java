package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
	private ImageView logout;

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
    		this.app.activeLinkMenu(this.menu, this.linkFees);
        });
    	
    	this.linkUsers.setOnMouseClicked(clickEvent -> {
    		this.app.initUsers(UserType.MEMBER);
    		this.app.activeLinkMenu(this.menu, this.linkUsers);
        });
    	
    	this.logout.setOnMouseClicked(clickEvent -> {
        	this.app.initLogout();
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
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        this.menu.setVisible(true);
    	
        if (this.app.getLoggedUser() instanceof Employee) {        	
        	this.linkFees.setVisible(true);
    		this.linkFees.setManaged(true);
    		
        	this.linkUsers.setVisible(true);
        	this.linkUsers.setManaged(true);
        }	
        
        if (this.app.getLoggedUser() instanceof Member) {
			Member member = (Member) this.app.getLoggedUser();
			
    		if(!this.app.getClub().checkPaymentFee(member, null, FeeType.MEMBERSHIP)) {
        		this.app.showAlert(Alert.AlertType.INFORMATION, "Payments error.", null, "Unpaid membership fee.");
        		
        		this.app.activeLinkMenu(this.menu, this.linkPayments);
        		this.app.toggleLinkMenu(this.menu, true);
        		this.app.initPayments(FeeType.MEMBERSHIP);
        		
        		return;
        	} else {
        		this.app.toggleLinkMenu(this.menu, false);
        	}
		}
        
        this.app.activeLinkMenu(this.menu, this.linkBoats);
		this.app.initBoats();
    }
}
