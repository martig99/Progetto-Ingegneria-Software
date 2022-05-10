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
import javafx.scene.text.*;

/**
 * The class {@code BoatsController} supports the display of all boats. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class BoatsController {

	private App app;
	
	@FXML
	private Text info;

	@FXML
    private TableView<Boat> boatsTable;

    @FXML
    private TableColumn<Boat, Number> idColumn, lengthColumn, storageFeeColumn;
    
    @FXML
    private TableColumn<Boat, String> nameColumn, ownerColumn;
    
    @FXML
    private Button addButton;

    /**
     * {@inheritDoc} 
    **/
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.addButton.setOnMouseClicked(event -> {    		
    		this.app.initUpsertBoat(null);
        });
		
		this.boatsTable.setOnMouseClicked(event -> {
			if (this.boatsTable.getSelectionModel().getSelectedItem() != null) {
				Boat boat = this.boatsTable.getSelectionModel().getSelectedItem();
	    		if (boat != null) {
					if (event.getClickCount() == 2) {
		    			this.removeBoat(boat);
					}
		    		
					if (event.getButton() == MouseButton.SECONDARY) {
						this.app.initUpsertBoat(boat);
					}
	    		}
			}
        });
    }
    
    /**
     * Removes a selected boat from the table. 
     * 
     * @param boat the boat to be removed.
    **/
    public void removeBoat(final Boat boat) {  
    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a boat", "You are removing the boat with unique identifier " + boat.getId(), "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.app.isSuccessfulMessage(ClientHelper.getResponseType(new Request(RequestType.REMOVE_BOAT, Arrays.asList(boat.getId()))));
    		this.setTableContent();
    	}
    }
    
    /**
	 * Sets the boats table with columns id, name, length, storage fee and email address of the member.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		this.lengthColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLength()));

		
		Fee fee = ClientHelper.getObjectResponse(new Request(RequestType.GET_FEE_BY_TYPE, Arrays.asList(FeeType.STORAGE)), Fee.class);
		this.storageFeeColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(this.app.setFloatFormat(cellData.getValue().getLength() * fee.getAmount())));
		
		this.ownerColumn.setCellValueFactory(cellData -> {
			User owner = cellData.getValue().getOwner();
			if (owner != null) {
				return new SimpleStringProperty(owner.getEmail());
			}
			
			return null;
		});		
	}
    
    /**
	 * Inserts the data of each boat in the table.
	**/
    public void setTableContent() {
    	User user = null;
		if (this.app.getLoggedUser() instanceof Member) {
			user = this.app.getLoggedUser();
		}
		
    	ObservableList<Boat> boats = FXCollections.<Boat>observableArrayList();		
        boats.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_BOATS, Arrays.asList(user)), Boat.class));
		this.boatsTable.setItems(boats);
    }

    /**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        this.info.setText("Double click to delete a boat.\nRight click to update a boat.");
        
        if (this.app.getLoggedUser() instanceof Employee) {
        	this.ownerColumn.setVisible(true);
        }
        
        this.setTableContent();
    }
}