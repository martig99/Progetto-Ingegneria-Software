package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

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
		if (this.email.getText().isEmpty() || this.password.getText().isEmpty()) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
    		return;
    	}
		
    	RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
    	String userType = selectedRadioButton.getText();
    	
    	User user = new User();
    	user.setEmail(this.email.getText());
    	user.setPassword(this.password.getText());
    	
    	Request request = new Request(RequestType.LOGIN_USER, user, UserType.valueOf(userType.toUpperCase()));
    	Object obj = ClientHelper.getResponse(request);
    	
    	if (obj instanceof Response) {
			Response response = (Response) obj;
			
			if (response.getObject() != null && response.getObject() instanceof User) {
				User loggedUser = (User) response.getObject();
				this.app.setLoggedUser(loggedUser);					
				this.app.initMainPage();
				
			} else if (response.getResponseType() != null) {
				this.app.getMessage(response.getResponseType());
			}
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
