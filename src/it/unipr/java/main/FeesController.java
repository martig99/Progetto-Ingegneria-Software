package it.unipr.java.main;

import it.unipr.java.model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

/**
 * The class {@code FeesController} supports the display of all fees. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class FeesController {

	private App app;
	
	@FXML
	private Text info;

	@FXML
    private TableView<Fee> feesTable;

    @FXML
    private TableColumn<Fee, Number> idColumn, amountColumn, validityPeriodColumn;
    
    @FXML
    private TableColumn<Fee, String> typeColumn;
    
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.feesTable.setOnMouseClicked(event -> {
			if (this.feesTable.getSelectionModel().getSelectedItem() != null) {
				int id = this.feesTable.getSelectionModel().getSelectedItem().getId();
	    		
				if (event.getButton() == MouseButton.SECONDARY) {
					this.app.initUpdateFee(id);
				}
			}
        });
    }
    
    /**
	 * Sets the fees table with columns id, type, amount and validity period.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
		this.amountColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()));
		this.validityPeriodColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValidityPeriod()));
		}
    
    /**
	 * Inserts the data of each boat in the table.
	**/
    public void setTableContent() {    	
    	ObservableList<Fee> fees = FXCollections.<Fee>observableArrayList();
    	
    	fees.addAll(this.app.getClub().getAllFees());
        
        this.feesTable.refresh();
		this.feesTable.setItems(fees);
    }

    /**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        this.info.setText("Right click to update a fee."); 
        this.setTableContent();
    }
}
