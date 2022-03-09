package it.unipr.java.main;

import it.unipr.java.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * The class {@code RacesController} supports the display of all races. 
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class RacesController {

	private App app;
	
	@FXML
	private Text info;

	@FXML
    private TableView<Race> racesTable;

    @FXML
    private TableColumn<Race, Number> idColumn, boatsNumberColumn, registrationFeeColumn;
    
    @FXML
    private TableColumn<Race, String> nameColumn, placeColumn, dateColumn, endDateRegistrationColumn;
    
    @FXML
    private TableColumn<Race, Button> viewRegistrationsColumn;

    @FXML
    private Button addButton;
    
    @FXML
    private void initialize() {	
		this.setTable();
		
		this.addButton.setOnMouseClicked(event -> {    		
    		this.app.initUpsertRace(null);
        });
		
		this.racesTable.setOnMouseClicked(event -> {
			if (this.app.getLoggedUser() instanceof Employee) {
				if (this.racesTable.getSelectionModel().getSelectedItem() != null) {
					int id = this.racesTable.getSelectionModel().getSelectedItem().getId();

					if (event.getClickCount() == 2) {
						this.removeRace(id);
					}
	    		
					if (event.getButton() == MouseButton.SECONDARY) {
						this.app.initUpsertRace(id);
					}
				}
			}
        });
		
		this.racesTable.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
    		if (event.getCode() == KeyCode.SPACE) {
    			this.insertBoatRegistration();
            }
        });
    }
    
    /**
     * Removes a selected race from the table. 
    **/
    public void removeRace(final int id) {   	
    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a race", "You are removing the race with unique identifier " + id, "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.app.getClub().removeRace(id);
    		this.setTableContent();
    		
    		this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The race has been removed correctly.");
    	}
    }
    
    /**
     * 
    **/
    public void insertBoatRegistration() {
    	Race race = this.racesTable.getSelectionModel().getSelectedItem();
    	if (race != null) {
			if (race.getEndDateRegistration().before(new Date())) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Registrations for this race are closed.");
				return;
			} 
			
			int participants = this.app.getClub().getNumberParticipantsInRace(race) + 1;
			if (participants > race.getBoatsNumber()) {
				this.app.showAlert(Alert.AlertType.WARNING, "Error", null, "Registrations for this race are sold out.");
				return;
			}
			
			this.app.initUpsertBoatRegistration(race.getId(), null);
    	}
    }
    
    /**
	 * Sets the races table with columns id, name, place, date, boats number and registration fee.
	**/
	public void setTable() {
		this.idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
		this.nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		this.placeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlace()));
		this.dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getDate())));
		this.boatsNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBoatsNumber()));		
		this.endDateRegistrationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(this.app.setDateFormat(cellData.getValue().getEndDateRegistration())));
		this.registrationFeeColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRegistrationFee()));		

		this.viewRegistrationsColumn.setCellFactory(new Callback<TableColumn<Race, Button>, TableCell<Race, Button>>() {
            @Override
            public TableCell<Race, Button> call(final TableColumn<Race, Button> param) {
                final TableCell<Race, Button> cell = new TableCell<Race, Button>() {
                    final Button btn = new Button("Registrations");

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                        	Race race = this.getTableView().getItems().get(getIndex());                       	
                        	ArrayList<RaceRegistration> registrations = app.getClub().getAllRegistrationsByRace(race);
                        	
                        	if (!registrations.isEmpty()) {
	                            btn.setOnAction(event -> {
	                                app.initRegistrations(race.getId());
	                            });
	                            
	            				btn.setStyle("-fx-background-color: transparent; -fx-border-color: #0eaae6; -fx-border-radius: 5px; -fx-text-fill: #0eaae6; -fx-font-weight: bold;");
	                            setGraphic(btn);
                        	}
                        }
                    }
                };
                
                return cell;
            }
        });
	}
    
    /**
	 * Inserts the data of each race in the table.
	**/
    public void setTableContent() {    	
    	ObservableList<Race> races = FXCollections.<Race>observableArrayList();
    	
        races.addAll(this.app.getClub().getAllRaces());
        
        this.racesTable.refresh();
		this.racesTable.setItems(races);
    }
    
    /**
     * Removes a selected race from the table. 
    **/
    public void removeRace() {
    	int id = this.racesTable.getSelectionModel().getSelectedItem().getId();
    	
    	Optional<ButtonType> result = this.app.showAlert(Alert.AlertType.CONFIRMATION, "Remove a race", "You are removing the race with unique identifier " + id, "Are you sure?");
    	if (result.get() == ButtonType.OK){
    		this.app.getClub().removeRace(id);
    		this.setTableContent();
    		
    		this.app.showAlert(Alert.AlertType.INFORMATION, "Excellent!", null, "The race has been removed correctly.");
    	}
    }
    
    /**
     * Sets the reference to the app application.
>>>>>>> branch 'master' of https://github.com/martig99/Progetto-Ingegneria-Software.git
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        String textInfo = "";
        if (this.app.getLoggedUser() instanceof Employee) {
        	textInfo = "Double click to delete a race.\nRight click to update a race.\n";
        } else {
        	this.app.setVisibleElement(this.addButton, false);
        }
        
        textInfo += "Spacebar to register a boat for a race.";
        this.info.setText(textInfo);
       
        this.setTableContent();
    }
}
