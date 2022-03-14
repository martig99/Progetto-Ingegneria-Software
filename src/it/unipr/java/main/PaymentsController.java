package it.unipr.java.main;

import it.unipr.java.model.*;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * The class {@code UsersController} supports the display of all users. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
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
    private Button payMembershipFeeButton, payStorageFeeButton;
    
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
		
		this.payMembershipFeeButton.setOnMouseClicked(event -> {
			this.app.initPayFee(FeeType.MEMBERSHIP);
		});
		
		this.payStorageFeeButton.setOnMouseClicked(event -> {
			this.app.initPayFee(FeeType.STORAGE);
		});
    }
    
    /**
	 * Sets the users table with columns id, fiscal code, first name, last name and email.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getDate())));
		this.validityStartDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getValidityStartDate())));
		this.validityEndDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getValidityEndDate())));
		this.memberColumn.setCellValueFactory(cellData -> {
			Member member = cellData.getValue().getMember();
			if (member != null) {
				return new SimpleStringProperty(member.getEmail());
			}
			
			return null;
		});
		
		this.totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()));
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
	 * Inserts the data of each user in the table.
	**/
    public void setTableContent(final FeeType feeType) { 
    	ObservableList<Payment> payments = FXCollections.<Payment>observableArrayList();    
    	
    	User user = null;
    	if (this.app.getLoggedUser() instanceof Member) {
        	user = (Member) this.app.getLoggedUser();
        }
    	
    	payments.addAll(this.app.getClub().getAllPayments(user, feeType));
        this.paymentsTable.refresh();
		this.paymentsTable.setItems(payments);
    }
    
    /**
     * 
    **/
    public void displayPaymentsMembershipFeesTable() {
    	this.app.activeLinkMenu(this.menu, this.membershipFees);	
    	
    	this.validityStartDateColumn.setVisible(true);
    	this.validityEndDateColumn.setVisible(true);
    	this.raceColumn.setVisible(false);
    	this.boatColumn.setVisible(false);
    	
    	this.app.setVisibleElement(this.payMembershipFeeButton, true);
    	this.app.setVisibleElement(this.payStorageFeeButton, false);
    	
		this.setTableContent(FeeType.MEMBERSHIP);
		this.setTable();
    }
    
    /**
     * 
    **/
    public void displayPaymentsStorageFeesTable() {
    	this.app.activeLinkMenu(this.menu, this.storageFees);	
    	
    	this.validityStartDateColumn.setVisible(true);
    	this.validityEndDateColumn.setVisible(true);
    	this.raceColumn.setVisible(false);
    	this.boatColumn.setVisible(true);
    	
    	this.app.setVisibleElement(this.payMembershipFeeButton, false);
    	this.app.setVisibleElement(this.payStorageFeeButton, true);
    	
		this.setTableContent(FeeType.STORAGE);
		this.setTable();
    }
    
    public void displayPaymentsRaceRegistrationFeesTable() {
    	this.app.activeLinkMenu(this.menu, this.raceRegistrationFees);
    	
    	this.validityStartDateColumn.setVisible(false);
    	this.validityEndDateColumn.setVisible(false);
    	this.raceColumn.setVisible(true);
    	this.boatColumn.setVisible(true);
    	
    	this.app.setVisibleElement(this.payMembershipFeeButton, false);
    	this.app.setVisibleElement(this.payStorageFeeButton, false);
    	
    	this.setTableContent(FeeType.RACE_REGISTRATION);
		this.setTable();
    }
    
    /**
     * 
     * @param feeType
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
        
        if (this.feeType == FeeType.MEMBERSHIP) {
        	this.displayPaymentsMembershipFeesTable();
        } else if (this.feeType == FeeType.STORAGE) {
        	this.displayPaymentsStorageFeesTable();
        } else if (this.feeType == FeeType.RACE_REGISTRATION) {
        	
        }
        
        if (this.app.getLoggedUser() instanceof Employee) {
        	this.memberColumn.setVisible(true);
        }
    }
}
