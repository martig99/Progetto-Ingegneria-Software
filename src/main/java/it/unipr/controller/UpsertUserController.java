package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * The class {@code CreateUserController} supports the creation of a new user in the application.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpsertUserController {
	
	private App app;
	private User user;
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
		if (this.user == null) {
			if ((this.userType == UserType.MEMBER && (this.fiscalCode.getText().trim().isEmpty() || this.address.getText().trim().isEmpty())) || this.firstName.getText().trim().isEmpty() || this.lastName.getText().trim().isEmpty() || this.email.getText().trim().isEmpty() || this.password.getText().trim().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
				return;
			}
		} else {
			if (this.fiscalCode.getText().trim().isEmpty() && this.firstName.getText().trim().isEmpty() && this.lastName.getText().trim().isEmpty() && this.address.getText().trim().isEmpty() && this.email.getText().trim().isEmpty() && this.password.getText().trim().isEmpty()) {	
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
				return;
			}
		}
			
		int idUser = this.user == null ? 0 : this.user.getId();
		String firstName = !this.firstName.getText().isEmpty() ? this.firstName.getText() : this.user.getFirstName();
		String lastName = !this.lastName.getText().isEmpty() ? this.lastName.getText() : this.user.getLastName();
		String email = !this.email.getText().isEmpty() ? this.email.getText() : this.user.getEmail();
		String password = !this.password.getText().isEmpty() ? this.password.getText() : this.user.getPassword();
		String fiscalCode = !this.fiscalCode.getText().isEmpty() ? this.fiscalCode.getText() : null;
		String address = !this.address.getText().isEmpty() ? this.address.getText() : null;
		Boolean admin = this.app.getLoggedUser() != null ? this.admin.isSelected() : false;
		
		Request request = new Request();
		
		if (this.user != null) {
			ResponseType resultUpdate = this.updateUser(idUser, firstName, lastName, email, password);
			if (resultUpdate == ResponseType.OK) {
				if (this.userType == UserType.MEMBER) {
					request.setRequestType(RequestType.UPDATE_MEMBER);
				} else {
					this.app.getMessage(resultUpdate);
					this.app.initUsers(this.userType);
					return;
				}
			} else {
				this.app.getMessage(resultUpdate);
				return;
			}
		} else {
			request.setRequestType(RequestType.INSERT_USER);
		}
		
		Member member = new Member();
		if (this.userType == UserType.MEMBER) {
			member = new Member(idUser, firstName, lastName, email, password, fiscalCode, address);		
			request.setPrimaryObject(member);
		} else {
			Employee employee = new Employee(idUser, firstName, lastName, email, password, admin);			
			request.setPrimaryObject(employee);
		}

		request.setSecondaryObject(this.userType);
		
		boolean result = this.app.getMessage(ClientHelper.getResponseType(request));
		if (result) {
			if (this.app.getLoggedUser() != null) {
				this.app.initUsers(this.userType);
			} else {
				this.app.initLogin();
			}
		}
	}	
	
	public ResponseType updateUser(final int id, final String firstName, final String lastName, final String email, final String password) {
		Request request = new Request(RequestType.UPDATE_USER, new User(id, firstName, lastName, email, password), null);
		return ClientHelper.getResponseType(request);
	}
	
	/**
	 * Sets the unique identifier of the user.
	 * 
	 * @param id
	 */
	public void setUser(final User user) {
		this.user = user;
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
        	if (this.user == null) {
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
            		this.title.setText("UPDATE THE MEMBER: " + this.user.getEmail());
	        	} else {
	        		this.title.setText("UPDATE THE EMPLOYEE: " + this.user.getEmail());
	        		
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
