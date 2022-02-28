package it.unipr.java.main;

import java.util.Optional;

import it.unipr.java.model.*;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.text.Text;

/**
 * The class {@code BoatsController} supports the display of all boats. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class BoatsController {

	private App main;
	
	@FXML
	private Text info;

	@FXML
    private TableView<Boat> boatsTable;

    @FXML
    private TableColumn<Boat, Number> idColumn;
    
    @FXML
    private TableColumn<Boat, String> nameColumn;
    
    @FXML
    private TableColumn<Boat, Number> lengthColumn;
    
    @FXML
    private TableColumn<Boat, Number> storageFeeColumn;
    
    @FXML
    private TableColumn<Boat, String> ownerColumn;
    
    @FXML
    private Button addButton;
    
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.addButton.setOnMouseClicked(event -> {    		
    		this.main.initAddBoat();
        });
		
		this.boatsTable.setOnMouseClicked(event -> {
    		if (event.getClickCount() == 2 && this.boatsTable.getSelectionModel().getSelectedItem() != null) {
				this.removeBoat();
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
		this.ownerColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(this.main.getClub().getUserById(cellData.getValue().getOwner()).getEmail()));		
	}
    
    /**
	 * Inserts the data of each boat in the table.
	**/
    public void setTableContent() {    	
    	ObservableList<Boat> boats = FXCollections.<Boat>observableArrayList();
    	
    	Member member = null;
    	if (this.main.getLoggedUser() instanceof Member) {
        	member = (Member) this.main.getLoggedUser();
        }
    	
        boats.addAll(this.main.getClub().getAllBoats(member));
        
        this.boatsTable.refresh();
		this.boatsTable.setItems(boats);
    }
    
    /**
     * Removes a selected boat from the table. 
    **/
    public void removeBoat() {
    	int id = this.boatsTable.getSelectionModel().getSelectedItem().getId();
    	
    	Optional<ButtonType> result = this.main.showAlert(Alert.AlertType.CONFIRMATION, "Remove a boat", "You are removing the boat with unique identifier " + id, "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.main.getClub().removeBoat(id);
    		this.setTableContent();
    		
			this.main.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The boat has been removed correctly.");
    	}
    }
    
    /**
     * Sets the reference to the main application.
     * 
     * @param main the reference to the main.
    **/
    public void setMain(final App main) {
        this.main = main;
        
        this.info.setText("Double click to delete a boat.");
        
        if (this.main.getLoggedUser() instanceof Employee) {
        	this.ownerColumn.setVisible(true);
        }
        
        this.setTableContent();
    }
}
