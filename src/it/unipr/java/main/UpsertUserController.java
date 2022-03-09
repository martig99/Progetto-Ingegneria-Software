package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;

/**
 * The class {@code CreateUserController} supports the creation of a new user in the application.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertUserController {
	
	private App app;
	private Integer idUser;
	private UserType userType;
	
	@FXML
    private Text title, link;
	
	@FXML
    private TextField fiscalCode, firstName, lastName, email, password, address;
	
	@FXML
	private CheckBox admin;
	
	@FXML
    private Button saveButton;
	
	@FXML
    private void initialize() {		
		this.saveButton.setOnMouseClicked(clickEvent -> {
        	this.upsertUser();
        });
        
        this.link.setOnMouseClicked(clickEvent -> {
        	if (this.app.getLoggedUser() != null) {
        		this.app.initUsers(this.userType);
        	} else {
        		this.app.initLogin();
        	}
	    });
    }
	
	/**
	 * 
	**/
	public void upsertUser() {  
		if (this.idUser == null) {
			if ((this.userType == UserType.MEMBER && (this.fiscalCode.getText().isEmpty() || this.address.getText().isEmpty())) || this.firstName.getText().isEmpty() || this.lastName.getText().isEmpty() || this.email.getText().isEmpty() || this.password.getText().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
				return;
			}
		} else {
			if (this.fiscalCode.getText().isEmpty() && this.firstName.getText().isEmpty() && this.lastName.getText().isEmpty() && this.address.getText().isEmpty() && this.email.getText().isEmpty() && this.password.getText().isEmpty()) {	
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
				return;
			}
		}
		
		if (!this.email.getText().isEmpty()) {
			if (!this.app.emailValidation(this.email.getText())) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please enter a valid email address.");
				return;
			}
		
			if (this.app.getClub().getUserByEmail(this.email.getText()) != null) {
				this.app.showAlert(Alert.AlertType.WARNING, "Attention", null, "Account already exists with the email entered.");
				return;
			}
		}
		
		if (!this.fiscalCode.getText().isEmpty() && this.app.getClub().getMemberByFiscalCode(this.fiscalCode.getText()) != null)  {
			this.app.showAlert(Alert.AlertType.WARNING, "Attention", null, "Account already exists with the fiscal code entered.");
			return;
		}
				
		String firstName = !this.firstName.getText().isEmpty() ? this.firstName.getText() : null;
		String lastName = !this.lastName.getText().isEmpty() ? this.lastName.getText() : null;
		String email = !this.email.getText().isEmpty() ? this.email.getText() : null;
		String password = !this.password.getText().isEmpty() ? this.password.getText() : null;
		String fiscalCode = !this.fiscalCode.getText().isEmpty() ? this.fiscalCode.getText() : null;
		String address = !this.address.getText().isEmpty() ? this.address.getText() : null;
		Boolean admin = this.app.getLoggedUser() != null ? this.admin.isSelected() : false;
		
		String message = "";
		if (this.idUser == null) {			
			this.app.getClub().createUser(fiscalCode, firstName, lastName, email, password, address, admin, this.userType);
			message = "The new user was created correctly.";
		} else {
			this.app.getClub().updateUser(this.idUser, firstName, lastName, email, password);	
			User user = this.app.getClub().getUserById(this.idUser);
			
			if(user instanceof Member) {
				this.userType = UserType.MEMBER;
				this.app.getClub().updateMember(this.idUser, fiscalCode, address);
			} else {
				this.userType = UserType.EMPLOYEE;
			}
			
			message = "The user has been updated correctly.";
		}
		
		this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, message);
		if (this.app.getLoggedUser() != null) {
			this.app.initUsers(this.userType);
		} else {
			this.app.initLogin();
		}
	}	
	
	/**
	 * Sets the unique identifier of the user.
	 * 
	 * @param id
	 */
	public void setIdUser(final Integer idUser) {
		this.idUser = idUser;
	}
	
	/**
	 * 
	 * @param userType
	**/
	public void setUserType(final UserType userType) {
		this.userType = userType;
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (this.app.getLoggedUser() != null && this.app.getLoggedUser() instanceof Employee) {
        	if (this.idUser == null) {
	        	if (this.userType == UserType.MEMBER) {
	        		this.title.setText("ADD A NEW MEMBER");
	        	} else {
	        		this.title.setText("ADD A NEW EMPLOYEE");
	        		
	        		this.app.setVisibleElement(this.fiscalCode, false);
	        		this.app.setVisibleElement(this.address, false);
	        		this.app.setVisibleElement(this.admin, true);
	        	}
        	} else {
        		if (this.userType == UserType.MEMBER) {
            		this.title.setText("UPDATE THE MEMBER: " + this.idUser);
	        	} else {
	        		this.title.setText("UPDATE THE EMPLOYEE: " + this.idUser);
	        		
	        		this.app.setVisibleElement(this.fiscalCode, false);
	        		this.app.setVisibleElement(this.address, false);
	        	}
        	}
        	
        	this.link.setText("GO BACK");
        } else {
        	this.title.setText("SIGN UP");
        	this.link.setText("LOGIN");
        }
    }
}
