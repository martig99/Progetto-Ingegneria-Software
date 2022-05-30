package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.fxml.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * The class {@code UsersController} supports the display of all users. 
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class UsersController {

	private App app;
	private UserType userType;

	@FXML
	private Text members, employees, info;
	
	@FXML
	private HBox menu;
	
	@FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Number> idColumn;
    
    @FXML
    private TableColumn<User, String> firstNameColumn, lastNameColumn, emailColumn, fiscalCodeColumn, addressColumn;
    
    @FXML
    private TableColumn<User, Boolean> administratorColumn;
    
    @FXML
    private Button addButton;
    
    /**
     * {@inheritDoc} 
    **/
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.addButton.setOnMouseClicked(event -> {    		
    		this.app.initUpsertUser(null, this.userType);
        });
		
		this.members.setOnMouseClicked(event -> {  	
			this.displayMembersTable();
        });
		
		this.employees.setOnMouseClicked(event -> {  
			this.displayEmployeesTable();
        });

		this.usersTable.setOnMouseClicked(event -> {
			if (this.app.getLoggedUser() instanceof Employee) {
				User selectedUser = this.usersTable.getSelectionModel().getSelectedItem();
				if (selectedUser != null) {				
					if (event.getClickCount() == 2) {
						this.removeUser(selectedUser);
					}
					
					if (event.getButton() == MouseButton.SECONDARY) {	
						this.updateUser(selectedUser);
					}
				}
			}
					
		});
	}
    
    /**
     * Removes a selected user from the table.
     * 
     * @param user the user to be removed.
    **/
    public void removeUser(final User user) {  	
    	if(user.getId() == this.app.getLoggedUser().getId()) {
    		this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "You cannot remove yourself.");
    	}
    	
    	if (this.app.getLoggedUser() instanceof Employee) {
    		Employee employee = (Employee) this.app.getLoggedUser();
		
			if(!employee.isAdministrator() || user instanceof Member) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "You cannot remove a club " + this.userType.toString().toLowerCase());
				return;
			}
			
	    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove an user", "You are removing the user with unique identifier " + user.getId(), "Are you sure?");
	    	if (result.get() == ButtonType.OK) {	    		
	    		Request request = new Request(RequestType.REMOVE_EMPLOYEE, Arrays.asList(user.getId()));
	    		this.app.isSuccessfulMessage(ClientHelper.getResponseType(request));
	    		this.setTableContent(this.userType);
	    	}
    	}
    }
    
    /**
     * Updates a selected user from the table.
     * 
     * @param user the user to be updated.
    **/
    public void updateUser(final User user) {
    	if(user.getId() != this.app.getLoggedUser().getId()) { 
			Employee employee = (Employee) this.app.getLoggedUser();
			if((!employee.isAdministrator() && user instanceof Member) || employee.isAdministrator()) {
    			this.app.initUpsertUser(user, this.userType);
    		} else {
	    		this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "You cannot update an employee.");
	    	}		
		} else {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "You cannot update yourself.");
		}
    }
    
    /**
	 * Sets the user table with the id, first name, last name and email address columns for all users. 
	 * Then for the members table also sets the fiscal code and address.
	 * Instead for the employee table sets a column where the value can be <code>true</code> or <code>false</code> depending on whether the employee is an administrator.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
		this.lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
		this.emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		
		this.fiscalCodeColumn.setCellValueFactory(cellData -> {
			User user = cellData.getValue();
			if (user instanceof Member) {
				Member member = (Member) user;
				return new SimpleStringProperty(member.getFiscalCode());
			}
			
			return null;
		});
		
		this.addressColumn.setCellValueFactory(cellData -> {
			User user = cellData.getValue();
			if (user instanceof Member) {
				Member member = (Member) user;
				return new SimpleStringProperty(member.getAddress());
			}
			
			return null;
		});
		
		this.administratorColumn.setCellValueFactory(cellData -> {
			User user = cellData.getValue();
			if (user instanceof Employee) {
				Employee employee = (Employee) user;
				return new SimpleBooleanProperty(employee.isAdministrator());
			}
			
			return null;
		});
	}
	
    /**
	 * Inserts the data of each user in the table.
	 * 
	 * @param userType the type of user.
	**/
    public void setTableContent(final UserType userType) { 
    	ObservableList<User> users = FXCollections.<User>observableArrayList(); 
    	ArrayList<User> list = ClientHelper.getListResponse(new Request(RequestType.GET_ALL_USERS, Arrays.asList(userType)), User.class);
    	users.addAll(list);
		this.usersTable.setItems(users);
    }
    
    /**
     * Displays items related to the members.
    **/
    public void displayMembersTable() {
    	this.app.activateLinkMenu(this.menu, this.members);
		
		this.fiscalCodeColumn.setVisible(true);
		this.addressColumn.setVisible(true);
		this.administratorColumn.setVisible(false);
		
		this.info.setText("Right click to update an user.");
		this.app.setVisibleElement(this.info, true);
		
		this.setUserType(UserType.MEMBER);
		this.setTableContent(this.userType);
		this.setTable();
		
		this.addButton.setText("ADD " + this.userType);
    }
    
    /**
     * Displays items related to the employees.
    **/
    public void displayEmployeesTable() {
    	this.app.activateLinkMenu(this.menu, this.employees);
		
		this.administratorColumn.setVisible(true);
		this.fiscalCodeColumn.setVisible(false);
		this.addressColumn.setVisible(false);
		
        this.info.setText("Double click to delete an user.\nRight click to update an user.");
		if (this.app.getLoggedUser() instanceof Employee) {
			Employee employee = (Employee) this.app.getLoggedUser();
			if (!employee.isAdministrator()) {
				this.app.setVisibleElement(this.info, false);
			}
		}
		
		this.setUserType(UserType.EMPLOYEE);
		this.setTableContent(this.userType);
		this.setTable();
		
		this.addButton.setText("ADD " + this.userType);
    }
    
    /**
     * Sets the type of user to handle.
     * 
     * @param userType the type of user.
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
        
        if (this.app.getLoggedUser() instanceof Employee) {
    		Employee employee = (Employee) this.app.getLoggedUser();
		
			if(!employee.isAdministrator()) {
				this.app.setVisibleElement(this.employees, false);
			}
    	}
        
        if (this.userType == UserType.EMPLOYEE) {
        	this.app.activateLinkMenu(this.menu, this.employees);
        	this.displayEmployeesTable();
        } else {
        	this.app.activateLinkMenu(this.menu, this.members);
	        this.displayMembersTable();
        }
    }
}
