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
	
	private App app;
	
	@FXML
    private TextField email, password;
	
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
        	this.app.initUpsertUser(null, UserType.MEMBER);
        });
    }
	
	/**
	 * Performs user's login.
	**/
	public void login() {
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
    	String userType = selectedRadioButton.getText();
    	
    	if (!this.email.getText().isEmpty() && !this.password.getText().isEmpty()) {
    		if (this.app.emailValidation(this.email.getText())) {
		    	User loggedUser = this.app.getClub().login(email.getText(), password.getText(), UserType.valueOf(userType.toUpperCase()));
				
				if (loggedUser != null) {
					this.app.setLoggedUser(loggedUser);					
					this.app.initMainPage();
				} else {
					this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "The credentials entered are incorrect.");            
				}
    		} else {
    			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please enter a valid email address.");
    		}
    	} else {
    		this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please enter email and password.");
    	}
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
    }
}
