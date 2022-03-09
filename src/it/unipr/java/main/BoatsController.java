package it.unipr.java.main;

import java.util.Optional;

import it.unipr.java.model.*;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.text.Text;

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
    
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.addButton.setOnMouseClicked(event -> {    		
    		this.app.initUpsertBoat(null);
        });
		
		this.boatsTable.setOnMouseClicked(event -> {
			if (this.boatsTable.getSelectionModel().getSelectedItem() != null) {
				int id = this.boatsTable.getSelectionModel().getSelectedItem().getId();
	    		
				if (event.getClickCount() == 2) {
	    			this.removeBoat(id);
				}
	    		
				if (event.getButton() == MouseButton.SECONDARY) {
					this.app.initUpsertBoat(id);
				}
			}
        });
    }
    
    /**
	 * Sets the boats table with columns id, name, length, storage fee and owner's email.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		this.lengthColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLength()));
		this.storageFeeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getStoragFee()));
		this.ownerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner().getEmail()));		
	}
    
    /**
	 * Inserts the data of each boat in the table.
	**/
    public void setTableContent() {    	
    	ObservableList<Boat> boats = FXCollections.<Boat>observableArrayList();
    	
    	Member member = null;
    	if (this.app.getLoggedUser() instanceof Member) {
        	member = (Member) this.app.getLoggedUser();
        }
    	
        boats.addAll(this.app.getClub().getAllBoats(member));
        
        this.boatsTable.refresh();
		this.boatsTable.setItems(boats);
    }
    
    /**
     * Removes a selected boat from the table. 
    **/
    public void removeBoat(final int id) {   	
    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a boat", "You are removing the boat with unique identifier " + id, "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.app.getClub().removeBoat(id);
    		this.setTableContent();
    		
			this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The boat has been removed correctly.");
    	}
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
