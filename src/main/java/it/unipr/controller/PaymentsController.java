package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.util.*;

import javafx.fxml.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * The class {@code PaymentsController} supports the display of all payments. 
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class PaymentsController {

	private App app;
	private FeeType feeType;
	
	@FXML
	private Text membershipFees, storageFees, raceRegistrationFees;
	
	@FXML
	private HBox menu;

	@FXML
    private TableView<Payment> paymentsTable;

    @FXML
    private TableColumn<Payment, Number> idColumn, totalColumn;
    
    @FXML
    private TableColumn<Payment, String> dateColumn, memberColumn, boatColumn, validityStartDateColumn, validityEndDateColumn, raceColumn, paymentServiceColumn;
    
    @FXML
    private Button notifyPaymentButton, payFeeButton;
    
    /**
     * {@inheritDoc}
    **/
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.membershipFees.setOnMouseClicked(event -> {  	
			this.displayPaymentsMembershipFeesTable();
        });
		
		this.storageFees.setOnMouseClicked(event -> {
			this.displayPaymentsStorageFeesTable();
		});
		
		this.raceRegistrationFees.setOnMouseClicked(event -> {
			this.displayPaymentsRaceRegistrationFeesTable();
		});
		
		this.notifyPaymentButton.setOnMouseClicked(event -> {
			this.app.initNotifyPayment(this.feeType);
		});
		
		this.payFeeButton.setOnMouseClicked(event -> {
			this.app.initPayFee(this.feeType);
		});
    }
    
    /**
	 * Sets the payments table with columns id, date, start of validity date, end of validity date, email address of the member, 
	 * total, description of the payment service, description of the race, name of the boat.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getDate())));
		this.validityStartDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getValidityStartDate())));
		this.validityEndDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getValidityEndDate())));
		this.memberColumn.setCellValueFactory(cellData -> {
			User member = cellData.getValue().getUser();
			if (member != null) {
				return new SimpleStringProperty(member.getEmail());
			}
			
			return null;
		});
		
		this.totalColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(this.app.setFloatFormat(cellData.getValue().getTotal())));
		this.paymentServiceColumn.setCellValueFactory(cellData -> {
			PaymentService paymentService = cellData.getValue().getPaymentService();
			if (paymentService != null) {
				return new SimpleStringProperty(paymentService.getDescription());
			}
			
			return null;
		});
		
		this.raceColumn.setCellValueFactory(cellData -> {
			RaceRegistration raceRegistration = cellData.getValue().getRaceRegistration();
			if (raceRegistration != null) {
				Race race = raceRegistration.getRace();
				if (race != null) {
					String raceDescription = race.getName() + " - " + this.app.setDateFormat(race.getDate());
					return new SimpleStringProperty(raceDescription);
				}
			}
			
			return null;
		});
		
		this.boatColumn.setCellValueFactory(cellData -> {
			Boat boat = cellData.getValue().getBoat();
			if (boat != null) {
				return new SimpleStringProperty(boat.getName());
			}
			
			return null;
		});
	}
	
    /**
	 * Inserts the data of each payment in the table.
	**/
    public void setTableContent() {
    	User user = null;
		if (this.app.getLoggedUser() instanceof Member) {
			user = this.app.getLoggedUser();
		}
		
    	ObservableList<Payment> payments = FXCollections.<Payment>observableArrayList();  
    	ArrayList<Payment> list = ClientHelper.getListResponse(new Request(RequestType.GET_ALL_PAYMENTS, Arrays.asList(user, this.feeType)), Payment.class);
    	payments.addAll(list);
		this.paymentsTable.setItems(payments);
    }
    
    /**
     * Displays items related to the payments of membership fee.
    **/
    public void displayPaymentsMembershipFeesTable() {
    	this.app.activateLinkMenu(this.menu, this.membershipFees);	
    	
    	this.validityStartDateColumn.setVisible(true);
    	this.validityEndDateColumn.setVisible(true);
    	this.raceColumn.setVisible(false);
    	this.boatColumn.setVisible(false);
    	
    	this.app.setVisibleElement(this.payFeeButton, true);
    	this.payFeeButton.setText("PAY MEMBERSHIP FEE");
    	
		this.setFeeType(FeeType.MEMBERSHIP);
		this.setTableContent();
		this.setTable();
    }
    
    /**
     * Displays items related to the payments of storage fee. 
    **/
    public void displayPaymentsStorageFeesTable() {
    	this.app.activateLinkMenu(this.menu, this.storageFees);	
    	
    	this.validityStartDateColumn.setVisible(true);
    	this.validityEndDateColumn.setVisible(true);
    	this.raceColumn.setVisible(false);
    	this.boatColumn.setVisible(true);
    	
    	this.app.setVisibleElement(this.payFeeButton, true);
    	this.payFeeButton.setText("PAY STORAGE FEE");
    	
		this.setFeeType(FeeType.STORAGE);
		this.setTableContent();
		this.setTable();
    }
    
    /**
     * Displays items related to the payments of race registration fee. 
    **/
    public void displayPaymentsRaceRegistrationFeesTable() {
    	this.app.activateLinkMenu(this.menu, this.raceRegistrationFees);
    	
    	this.validityStartDateColumn.setVisible(false);
    	this.validityEndDateColumn.setVisible(false);
    	this.raceColumn.setVisible(true);
    	this.boatColumn.setVisible(true);
    	
    	this.app.setVisibleElement(this.notifyPaymentButton, false);
    	this.app.setVisibleElement(this.payFeeButton, false);
    	
    	this.setFeeType(FeeType.RACE_REGISTRATION);
    	this.setTableContent();
		this.setTable();
    }
    
    /**
     * Sets the type of fee for which payments should be handled.
     * 
     * @param feeType the type of fee.
    **/
    public void setFeeType(final FeeType feeType) {
    	this.feeType = feeType;
    }
    
    /**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        if (this.app.getLoggedUser() instanceof Employee) {
    		this.app.setVisibleElement(this.notifyPaymentButton, true);
        	this.memberColumn.setVisible(true);
        }
        
        if (this.feeType == FeeType.MEMBERSHIP) {
        	this.displayPaymentsMembershipFeesTable();
        } else if (this.feeType == FeeType.STORAGE) {
        	this.displayPaymentsStorageFeesTable();
        } else if (this.feeType == FeeType.RACE_REGISTRATION) {
        	this.displayPaymentsRaceRegistrationFeesTable();
        }
    }
}
