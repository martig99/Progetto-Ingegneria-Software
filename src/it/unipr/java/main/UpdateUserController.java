package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The class {@code UpdateUserController} supports the update of the user. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpdateUserController {
	
	private App main;
	private int id;
	
	@FXML
    private TextField fiscalCode;

	@FXML
    private TextField address;
	
	@FXML
    private TextField firstName;
	
	@FXML
    private TextField lastName;
		
	@FXML
    private TextField email;
	
	@FXML
	private PasswordField password;
	
		
	@FXML
    private Button addButton;
	
	@FXML
    private void initialize() {	
		this.addButton.setOnMouseClicked(event -> {    		
    		this.updateUser();
        });
    }
	
	/**
	 * Adds a boat owned by the logged in user or a club member chosen by an employee.
	**/
	public void updateUser() {
		if (!this.firstName.getText().isEmpty() || !this.lastName.getText().isEmpty() || !this.email.getText().isEmpty() || !this.password.getText().isEmpty() || !this.fiscalCode.getText().isEmpty() || !this.address.getText().isEmpty()) {
			{
				if (this.main.getClub().getUserByEmail(this.email.getText()) != null && this.main.getClub().getMemberByFiscalCode(this.fiscalCode.getText()) != null) {
					this.main.showAlert(Alert.AlertType.WARNING, "Attention", null, "Account already exists with the fiscal code and/or the email entered.");
				} else {
						//codice per aggiornare l'user
						String fName = null,lName = null,e = null,pw = null;
						if(!this.firstName.getText().isEmpty())
							fName = this.firstName.getText();
						if(!this.lastName.getText().isEmpty())
							lName = this.lastName.getText();
						if(!this.email.getText().isEmpty())
							e = this.email.getText();
						if(!this.password.getText().isEmpty())
							pw = this.password.getText();
						
						this.main.getClub().updateUser(this.id, fName, lName, e, pw);	
						User u = this.main.getClub().getUserById(id);
						if(u instanceof Member)
						{
							String fisC = null, a = null;
							if(!this.fiscalCode.getText().isEmpty())
								fisC = this.fiscalCode.getText();
							if(!this.address.getText().isEmpty())
								a = this.address.getText();
							this.main.getClub().updateMember(this.id,fisC,a);
						}
						/*else
						{
							//per adesso nulla poi chiedi a martina se vuole che altri dipendenti diventino capi
						}*/
						this.main.showAlert(Alert.AlertType.INFORMATION, "Thanks", null, "The user has been updated correctly.");
						this.main.initUsers();
					 	Stage stage = (Stage) this.addButton.getScene().getWindow();
					    stage.close();
					}
			}
			
		} else {
			this.main.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
		}
	}
	
	/**
	 * Sets the unique identifier of the boat
	 * 
	 * @param id
	 */
	public void setIdUser(final int id) {
		this.id = id;
	}
	
	
	/**
     * Sets the reference to the main application.
     * 
     * @param main the reference to the main.
    **/
    public void setMain(final App main) {
        this.main = main;
        User u = this.main.getClub().getUserById(id);
        if(u instanceof Employee)
        {
        	this.main.setVisibleElement(fiscalCode, false);
        	this.main.setVisibleElement(address, false);
        }
    }
	
}
