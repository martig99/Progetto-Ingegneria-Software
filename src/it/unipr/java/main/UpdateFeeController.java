package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * The class {@code UpdateFeeController} supports the update a fee. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class UpdateFeeController {
	
	private App app;
	private Integer idFee;
	
	@FXML
	private Text title, link;
	
	@FXML
    private TextField amount, validityPeriod;
	
	@FXML
    private Button updateButton;
	
	@FXML
    private void initialize() {	
		this.updateButton.setOnMouseClicked(event -> {
			this.updateFee();
        });
		
		this.link.setOnMouseClicked(event -> {
			this.app.initFees();
        });
	}

	/**
	 * 
	**/
	public void updateFee() {
		Integer amount = null, validityPeriod = null;
	
		if (this.amount.getText().isEmpty() && this.validityPeriod.getText().isEmpty()) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
			return;
		}
		
			
		if (!this.amount.getText().isEmpty()) {
			amount = this.app.convertToInteger(this.amount.getText());			
			if (amount <= 0) {
				return;
			}
		}
		if (!this.validityPeriod.getText().isEmpty()) {
			validityPeriod = this.app.convertToInteger(this.validityPeriod.getText());			
			if (validityPeriod <= 0) {
				return;
			}
		}
		
		Fee fee = this.app.getClub().getFeeById(this.idFee);
		//fee.setAmount(amount);
		//fee.setValidityPeriod(validityPeriod);
		
		this.app.getClub().removeFee(this.idFee);
		this.app.getClub().insertFee(fee.getType().toString(),amount,validityPeriod);
		this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The fee has been updated correctly.");
		this.app.initFees();
		
	}
	
	/**
	 * 
	 * @param id
	**/
	public void setIdFee(final Integer idFee) {
		this.idFee = idFee;
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        this.title.setText("UPDATE THE FEE WITH ID " + this.idFee);
    }
	
}
