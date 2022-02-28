package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;

/**
 * The class {@code LoginController} supports the login of a user in the application.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class LoginController {
	
	private App main;
	
	@FXML
    private TextField email;
	
	@FXML
    private TextField password;
	
	@FXML
	ToggleGroup group;
	
	@FXML
    private Button button;
	
	@FXML
	private Text linkSignUp;
	
	@FXML
    private void initialize() {		
        this.button.setOnMouseClicked(clickEvent -> {
        	this.login();
        });
        
        this.linkSignUp.setOnMouseClicked(clickEvent -> {
        	this.main.initCreateUser();
        });
    }
	
	/**
	 * Performs user's login.
	**/
	public void login() {
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
    	String role = selectedRadioButton.getText();
    	
    	if (!this.email.getText().isEmpty() && !this.password.getText().isEmpty()) {
    		if (this.main.emailValidation(this.email.getText())) {
		    	User loggedUser = this.main.getClub().login(email.getText(), password.getText(), UserRole.valueOf(role.toUpperCase()));
				
				if (loggedUser != null) {
					this.main.setLoggedUser(loggedUser);
					this.main.showAlert(Alert.AlertType.INFORMATION, "Login", null, loggedUser.toString());
					
					this.main.initMainPage();
				} else {
					this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "The credentials entered are incorrect.");            
				}
    		} else {
    			this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "Please enter a valid email address.");
    		}
    	} else {
    		this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "Please enter email and password.");
    	}
	}
	
	/**
     * Sets the reference to the main application.
     * 
     * @param main the reference to the main.
    **/
    public void setMain(final App main) {
        this.main = main;
    }
}
