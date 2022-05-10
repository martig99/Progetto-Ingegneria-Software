package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.io.Serializable;
import java.util.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * The class {@code CreateUserController} supports the section for the creation or updating of an user.
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
	
	/**
	 * {@inheritDoc} 
	**/
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
	 * Performs the creation or updating of the user.
	**/
	public void upsertUser() { 
		if (this.user == null) {
			if ((this.userType == UserType.MEMBER && (this.fiscalCode.getText().trim().isEmpty() || this.address.getText().trim().isEmpty())) || this.firstName.getText().trim().isEmpty() || this.lastName.getText().trim().isEmpty() || this.email.getText().trim().isEmpty() || this.password.getText().trim().isEmpty()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete all fields.");
				return;
			}
		} else {
			if (this.fiscalCode.getText().trim().isEmpty() && this.firstName.getText().trim().isEmpty() && this.lastName.getText().trim().isEmpty() && this.address.getText().trim().isEmpty() && this.email.getText().trim().isEmpty() && this.password.getText().trim().isEmpty() && !this.admin.isSelected()) {	
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
		Boolean admin = this.userType == UserType.EMPLOYEE ? this.admin.isSelected() : false;
		
		Request request = new Request();
		
		if (this.user != null) {
			ResponseType resultUpdate = this.updateUser(idUser, firstName, lastName, email, password);
			if (resultUpdate == ResponseType.OK) {
				if (this.userType == UserType.MEMBER) {
					request.setRequestType(RequestType.UPDATE_MEMBER);
				} else {
					request.setRequestType(RequestType.UPDATE_EMPLOYEE);
				}
			} else {
				this.app.isSuccessfulMessage(resultUpdate);
				return;
			}
		} else {
			request.setRequestType(RequestType.INSERT_USER);
		}
		
		List<Serializable> object = new ArrayList<Serializable>();
		
		if (this.userType == UserType.MEMBER) {
			Member member = new Member(idUser, firstName, lastName, email, password, fiscalCode, address, StatusCode.ACTIVE);		
			object.add(member);
		} else {
			Employee employee = new Employee(idUser, firstName, lastName, email, password, admin, StatusCode.ACTIVE);			
			object.add(employee);
		}

		object.add(this.userType);
		request.setObject(object);
		
		boolean result = this.app.isSuccessfulMessage(ClientHelper.getResponseType(request));
		if (result) {
			if (this.app.getLoggedUser() != null) {
				this.app.initUsers(this.userType);
			} else {
				this.app.initLogin();
			}
		}
	}	
	
	private ResponseType updateUser(final int id, final String firstName, final String lastName, final String email, final String password) {
		Request request = new Request(RequestType.UPDATE_USER, Arrays.asList(new User(id, firstName, lastName, email, password, StatusCode.ACTIVE), this.userType));
		return ClientHelper.getResponseType(request);
	}
	
	/**
	 * Sets the user to insert or update.
	 * 
	 * @param user the user.
	**/
	public void setUser(final User user) {
		this.user = user;
	}
	
	/**
	 * Sets the type to which the user relates.
	 * 
	 * @param userType the type of the user.
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
	        		this.app.setVisibleElement(this.admin, true);
	        	}
        	}
        	
        	this.link.setText("GO BACK");
        } else {
        	this.title.setText("SIGN UP");
        	this.link.setText("LOGIN");
        }
    }
}
