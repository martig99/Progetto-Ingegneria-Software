package it.unipr.java.main;

import it.unipr.java.model.UserRole;
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
	
	private App main;
	
	@FXML
    private Text title;
	
	@FXML
    private TextField fiscalCode;
	
	@FXML
    private TextField firstName;
	
	@FXML
    private TextField lastName;
	
	@FXML
    private TextField email;
	
	@FXML
    private TextField password;
	
	@FXML
    private Button saveButton;
	
	@FXML
	private Text link;
	
	@FXML
	private HBox boxRole;
	
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
        	if (this.main.getLoggedUser() != null) {
        		this.main.initUsers();
        	} else {
        		this.main.initLogin();
        	}
	    });
    }
	
	/**
	 * Creates a new user.
	**/
	public void createUser() {   
		if (!this.fiscalCode.getText().isEmpty() && !this.firstName.getText().isEmpty() && !this.lastName.getText().isEmpty() && !this.email.getText().isEmpty() && !this.password.getText().isEmpty()) {
			if (this.main.emailValidation(this.email.getText())) {
				if (this.main.getClub().getUser(this.fiscalCode.getText(), this.email.getText()) != null) {
					this.main.showAlert(Alert.AlertType.WARNING, "Attention", null, "Account already exists with the fiscal code and/or the email entered.");
				} else {
					if (this.main.getLoggedUser() != null) {
						RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
				    	String role = selectedRadioButton.getText();
				    	
						this.main.getClub().createUser(this.fiscalCode.getText(), this.firstName.getText(), this.lastName.getText(), this.email.getText(), this.password.getText(), this.admin.isSelected(), UserRole.valueOf(role.toUpperCase()));
						
    					this.main.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The new user was created correctly.");
    					this.main.initUsers();
					} else {
						this.main.getClub().createUser(this.fiscalCode.getText(), this.firstName.getText(), this.lastName.getText(), this.email.getText(), this.password.getText(), false, UserRole.MEMBER);
						
						this.main.showAlert(Alert.AlertType.INFORMATION, "Thanks", null, "Your account has been successfully created.");
						this.main.initLogin();
					}
				}
			} else {
    			this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "Please enter a valid email address.");
    		}
		} else {
			this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
		}
	}
	
	/**
     * Sets the reference to the main application.
     * 
     * @param main the reference to the main.
    **/
    public void setMain(final App main) {
        this.main = main;
        
        if (this.main.getLoggedUser() != null) {
        	this.title.setText("CREATE A NEW USER");
        	this.link.setText("GO BACK");
        	
        	this.boxRole.setVisible(true);
        	this.boxRole.setManaged(true);
        	this.admin.setVisible(true);
        	this.admin.setManaged(true);
        } else {
        	this.title.setText("SIGN UP");
        	this.link.setText("LOGIN");
        }
    }
}
