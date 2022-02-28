package it.unipr.java.main;

import it.unipr.java.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
	private Text linkBoats;
	
	@FXML
	private Text linkRaces;
	
	@FXML
	private Text linkPayments;
	
	@FXML
	private Text linkFees;
	
	@FXML
	private Text linkUsers;
	
	@FXML
	private ImageView logout;

    @FXML
    private void initialize() {
    	this.activeLink(this.linkBoats);
    	
    	this.linkBoats.setOnMouseClicked(clickEvent -> {
        	this.main.initBoats();
        	this.activeLink(this.linkBoats);
        });
    	
    	this.linkRaces.setOnMouseClicked(clickEvent -> {
        	this.activeLink(this.linkRaces);
        });
    	
    	this.linkPayments.setOnMouseClicked(clickEvent -> {
        	this.activeLink(this.linkPayments);
        });
    	
    	this.linkFees.setOnMouseClicked(clickEvent -> {
        	this.activeLink(this.linkFees);
        });
    	
    	this.linkUsers.setOnMouseClicked(clickEvent -> {
    		this.main.initUsers();
        	this.activeLink(this.linkUsers);
        });
    	
    	this.logout.setOnMouseClicked(clickEvent -> {
        	this.main.initLogout();
        });
    }
    
    /**
     * 
     * @param clickedLink the link of the clicked menu.
    **/
    public void activeLink (Text clickedLink) {
    	for (Node child: this.menu.getChildren()) {
    		if (child instanceof Text) {
    			Text link = (Text) child;
    			link.setUnderline(false);
    		}
    	}
    	
    	clickedLink.setUnderline(true);
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
        this.main.initBoats();
    }
}
