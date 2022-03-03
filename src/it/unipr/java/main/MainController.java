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
	
	private App main;
	
	@FXML
	private HBox menu;
	
	@FXML
	private Text linkBoats, linkRaces, linkPayments, linkFees, linkUsers;
	
	@FXML
	private ImageView logout;

    @FXML
    private void initialize() {    	
    	this.linkBoats.setOnMouseClicked(clickEvent -> {
        	this.main.initBoats();
        	this.main.activeLinkMenu(this.menu, this.linkBoats);
        });
    	
    	this.linkRaces.setOnMouseClicked(clickEvent -> {
    		this.main.activeLinkMenu(this.menu, this.linkRaces);
        });
    	
    	this.linkPayments.setOnMouseClicked(clickEvent -> {
    		this.main.activeLinkMenu(this.menu, this.linkPayments);
        });
    	
    	this.linkFees.setOnMouseClicked(clickEvent -> {
    		this.main.activeLinkMenu(this.menu, this.linkFees);
        });
    	
    	this.linkUsers.setOnMouseClicked(clickEvent -> {
    		this.main.initUsers();
    		this.main.activeLinkMenu(this.menu, this.linkUsers);
        });
    	
    	this.logout.setOnMouseClicked(clickEvent -> {
        	this.main.initLogout();
        });
    }
	
	/**
     * Sets the reference to the main application.
     * 
     * @param main the reference to the main.
    **/
    public void setMain(final App main) {
        this.main = main;
        
        if (this.main.getLoggedUser() instanceof Employee) {        	
        	this.linkFees.setVisible(true);
    		this.linkFees.setManaged(true);
    		
        	this.linkUsers.setVisible(true);
        	this.linkUsers.setManaged(true);
        }
        
        this.menu.setVisible(true);
    	this.main.activeLinkMenu(this.menu, this.linkBoats);
        
 
        if (this.main.getClub().checkPaymentMembershipFee(this.main.getLoggedUser().getId()) && 
        		this.main.getLoggedUser() instanceof Member) {
			this.main.showAlert(Alert.AlertType.INFORMATION, "CARICARE PAGINA PAGAMENTI", "Quota di associazione non pagata.", "ANCORA DA TERMINARE..");
        }
    	
    	this.main.initBoats();
    }
}
