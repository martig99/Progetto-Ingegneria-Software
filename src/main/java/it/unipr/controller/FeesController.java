package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import javafx.fxml.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

/**
 * The class {@code FeesController} supports the display of all fees. 
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
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
    
    /**
     * {@inheritDoc}
    **/
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.feesTable.setOnMouseClicked(event -> {
			if (this.feesTable.getSelectionModel().getSelectedItem() != null) {
				Fee fee = this.feesTable.getSelectionModel().getSelectedItem();
	    		
				if (event.getButton() == MouseButton.SECONDARY) {
					this.app.initUpdateFee(fee);
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
		this.amountColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(this.app.setFloatFormat(cellData.getValue().getAmount())));
		this.validityPeriodColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValidityPeriod()));
	}
    
    /**
	 * Inserts the data of each fee in the table.
	**/
    public void setTableContent() {    	
    	ObservableList<Fee> fees = FXCollections.<Fee>observableArrayList();
    	fees.addAll(ClientHelper.getListResponse(new Request(RequestType.GET_ALL_FEES), Fee.class));
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
