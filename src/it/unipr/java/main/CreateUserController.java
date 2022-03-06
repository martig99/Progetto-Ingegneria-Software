package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;

/**
 * The class {@code CreateUserController} supports the creation of a new user in the application.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class CreateUserController {
	
	private App app;
	
	@FXML
    private Text title, link;
	
	@FXML
    private TextField fiscalCode, firstName, lastName, email, password, address;
	
	@FXML
    private Button saveButton;
	
	@FXML
	private HBox boxUserType;
	
	@FXML
	private ToggleGroup group;

	@FXML
	private CheckBox admin;
	
	@FXML
    private void initialize() {		
		this.saveButton.setOnMouseClicked(clickEvent -> {
        	this.createUser();
        });
        
        this.link.setOnMouseClicked(clickEvent -> {
        	if (this.app.getLoggedUser() != null) {
        		this.app.initUsers();
        	} else {
        		this.app.initLogin();
        	}
	    });
    }
	
	/**
	 * Creates a new user.
	**/
	public void createUser() {   
		if (!this.fiscalCode.getText().isEmpty() && !this.firstName.getText().isEmpty() && !this.lastName.getText().isEmpty() && !this.email.getText().isEmpty() && !this.password.getText().isEmpty() && !this.address.getText().isEmpty()) {
			if (this.app.emailValidation(this.email.getText())) {
				if (this.app.getLoggedUser() != null && this.app.getClub().getUserByEmail(this.email.getText()) != null) {
					this.app.showAlert(Alert.AlertType.WARNING, "Attention", null, "Account already exists with the email entered.");
				} else if (this.app.getLoggedUser() != null && this.app.getClub().getMemberByFiscalCode(this.fiscalCode.getText()) != null)  {
					this.app.showAlert(Alert.AlertType.WARNING, "Attention", null, "Account already exists with the fiscal code entered.");
				} else {
					if (this.app.getLoggedUser() != null) {
						RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
				    	String userType = selectedRadioButton.getText();
				    	
						this.app.getClub().createUser(this.fiscalCode.getText(), this.firstName.getText(), this.lastName.getText(), this.email.getText(), this.password.getText(), this.address.getText(), this.admin.isSelected(), UserType.valueOf(userType.toUpperCase()));
						
    					this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The new user was created correctly.");
    					this.app.initUsers();
					} else {
						this.app.getClub().createUser(this.fiscalCode.getText(), this.firstName.getText(), this.lastName.getText(), this.email.getText(), this.password.getText(), this.address.getText(), false, UserType.MEMBER);
						
						this.app.showAlert(Alert.AlertType.INFORMATION, "Thanks", null, "Your account has been successfully created.");
						this.app.initLogin();
					}
				}
			} else {
    			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please enter a valid email address.");
    		}
		} else {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
		}
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (this.app.getLoggedUser() != null && this.app.getLoggedUser() instanceof Employee) {
        	Employee employee = (Employee) this.app.getLoggedUser();
        	if (employee.isAdministrator()) {
        		this.title.setText("CREATE A NEW MEMBER OR EMPLOYEE");

        		this.app.setVisibleElement(this.boxUserType, true);
        	} else {
        		this.title.setText("CREATE A NEW MEMBER");
        	}
        	
        	this.link.setText("GO BACK");
        } else {
        	this.title.setText("SIGN UP");
        	this.link.setText("LOGIN");
        }
    }
}
