package it.unipr.java.main;

import java.util.Optional;

import it.unipr.java.model.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * The class {@code UsersController} supports the display of all users. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UsersController {

	private App app;

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
    
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.addButton.setOnMouseClicked(event -> {    		
    		this.app.initCreateUser();
        });
		
		this.members.setOnMouseClicked(event -> {  	
			this.displayMembersTable();
        });
		
		this.employees.setOnMouseClicked(event -> {  
			this.displayEmployeesTable();
        });

		this.usersTable.setOnMouseClicked(event -> {
			if (this.app.getLoggedUser() instanceof Employee)
			{
				if (event.getClickCount() == 2 && this.usersTable.getSelectionModel().getSelectedItem() != null) {
					this.removeMember();
					}
				
				if (event.getButton() == MouseButton.SECONDARY && this.usersTable.getSelectionModel().getSelectedItem() != null){	
					int id = this.usersTable.getSelectionModel().getSelectedItem().getId();
					User u = this.app.getClub().getUserById(id);
					int n = this.app.getLoggedUser().getId();
					if(u.getId() != n) //da chiedere a martina se uno può modificare se stesso
			    	{
						Employee e = (Employee)this.app.getLoggedUser();
						if(!e.isAdministrator() && u instanceof Member || e.isAdministrator())
			    		{
			    			this.app.initUpdateUser(id);
			    		}
			    		else
				    	{
				    		this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "you cannot update an employee");
				    	}
			    			
					}
					else
					{
						this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "you cannot update yourself");
					}
				}
			}
					
		});
	}
    
    /**
     * Removes a selected member from the table
    **/
    public void removeMember() {
    	int id = this.usersTable.getSelectionModel().getSelectedItem().getId();
    	User u = this.app.getClub().getUserById(id);
    	int n = this.app.getLoggedUser().getId();
    	if(u.getId() != n)
    	{
    		Employee e = (Employee)this.app.getLoggedUser();
    		if(!e.isAdministrator() && u instanceof Member || e.isAdministrator())
    		{
		    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a user", "You are removing the user with unique identifier " + id, "Are you sure?");
		    	if (result.get() == ButtonType.OK){
		    		this.app.getClub().removeUser(id);
		    		//(modifica) aggiungere nel caso la rimozione anche delle barche collegate a quel socio
		    		this.setTableContent(UserType.MEMBER);
		    		
					this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The user has been removed correctly.");
		    	}
	    	}
	    	else
	    	{
	    		this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "you cannot remove an employee");
	    	}
    	}
    	else
    	{
    		this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "you cannot remove yourself");
    	}
    }
    
    /**
	 * Sets the users table with columns id, fiscal code, first name, last name and email.
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
	**/
    public void setTableContent(final UserType userType) { 
    	ObservableList<User> users = FXCollections.<User>observableArrayList();    	
    	users.addAll(this.app.getClub().getAllUser(userType));
        this.usersTable.refresh();
		this.usersTable.setItems(users);
    }
    
    public void displayMembersTable() {
    	this.app.activeLinkMenu(this.menu, this.members);
		
		this.fiscalCodeColumn.setVisible(true);
		this.addressColumn.setVisible(true);
		this.administratorColumn.setVisible(false);
		
		this.app.setVisibleElement(this.info, true);
		
		this.setTableContent(UserType.MEMBER);
		this.setTable();
    }
    
    public void displayEmployeesTable() {
    	this.app.activeLinkMenu(this.menu, this.employees);
		
		this.administratorColumn.setVisible(true);
		this.fiscalCodeColumn.setVisible(false);
		this.addressColumn.setVisible(false);
		
		if (this.app.getLoggedUser() instanceof Employee) {
			Employee employee = (Employee) this.app.getLoggedUser();
			if (!employee.isAdministrator()) {
				this.app.setVisibleElement(this.info, false);
			}
		}
		
		this.setTableContent(UserType.EMPLOYEE);
		this.setTable();
    }
    
    /**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        this.app.activeLinkMenu(this.menu, this.members);
        this.displayMembersTable();
        
        this.info.setText("Double click to delete an user.\nRight click to update an user.");
    }
}
