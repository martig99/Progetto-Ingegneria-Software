package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * The class {@code UpdateFeeController} supports the section for the updating a fee.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class UpdateFeeController {
	
	private App app;
	private Fee fee;
	
	@FXML
	private Text title, link;
	
	@FXML
    private TextField amount, validityPeriod;
	
	@FXML
    private Button updateButton;
	
	/**
	 * {@inheritDoc}
	**/
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
	 * Performs the updating of the fee.
	**/
	public void updateFee() {	
		if (this.amount.getText().isEmpty() && this.validityPeriod.getText().isEmpty()) {
			this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Please complete at least one field.");
			return;
		}
				
		float amount = 0;
		if (!this.amount.getText().isEmpty()) {
			amount = this.app.convertToFloat(this.amount.getText());			
			if (amount <= 0) {
				return;
			}
		} else {
			amount = this.fee.getAmount();
		}
		
		int validityPeriod = 0;
		if (!this.validityPeriod.getText().isEmpty()) {
			validityPeriod = this.app.convertToInteger(this.validityPeriod.getText());			
			if (validityPeriod <= 0) {
				return;
			}
		} else {
			validityPeriod = this.fee.getValidityPeriod();
		}
				
		boolean result = this.app.isSuccessfulMessage(ClientHelper.getResponseType(new Request(RequestType.UPDATE_FEE, 		Arrays.asList(new Fee(this.fee.getId(), this.fee.getType(), amount, validityPeriod, StatusCode.ACTIVE)))));
		if (result) {
			this.app.initFees();
		}
	}
	
	/**
	 * Sets the fee to be updated.
	 * 
	 * @param fee the fee.
	**/
	public void setFee(final Fee fee) {
		this.fee = fee;
	}
	
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        this.title.setText("UPDATE THE " + this.fee.getType() + " FEE");
    }
	
}
