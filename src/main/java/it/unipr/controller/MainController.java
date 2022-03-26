package main.java.it.unipr.controller;

import main.java.it.unipr.client.*;
import main.java.it.unipr.message.Request;
import main.java.it.unipr.message.RequestType;
import main.java.it.unipr.model.*;

import javafx.util.Duration;

import javafx.fxml.*;

import java.util.ArrayList;

import javafx.concurrent.*;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * The class {@code MainController} supports the menu and the different sections of the application.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class MainController {
	
	private App app;
	
	@FXML
	private HBox menu;
	
	@FXML
	private Text linkBoats, linkRaces, linkPayments, linkFees, linkUsers;
	
	@FXML
	private ImageView logout, notificationsImage;
	
	@FXML
	private StackPane notificationsBlock;
	
	@FXML
	private Label numberNotifications;

    @FXML
    private void initialize() {
    	this.setMenu();
    }
    
    /**
     * 
    **/
    public void setMenu() {
    	this.linkBoats.setOnMouseClicked(clickEvent -> {
        	this.app.initBoats();
        	this.app.activeLinkMenu(this.menu, this.linkBoats);
        });
    	
    	this.linkRaces.setOnMouseClicked(clickEvent -> {
    		this.app.initRaces();
    		this.app.activeLinkMenu(this.menu, this.linkRaces);
        });
    	
    	this.linkPayments.setOnMouseClicked(clickEvent -> {
    		this.app.initPayments(FeeType.MEMBERSHIP);
    		this.app.activeLinkMenu(this.menu, this.linkPayments);
        });
    	
    	this.linkFees.setOnMouseClicked(clickEvent -> {
    		this.app.initFees();
    		this.app.activeLinkMenu(this.menu, this.linkFees);
        });
    	
    	this.linkUsers.setOnMouseClicked(clickEvent -> {
    		this.app.initUsers(UserType.MEMBER);
    		this.app.activeLinkMenu(this.menu, this.linkUsers);
        });

    	this.notificationsImage.setOnMouseClicked(clickEvent -> {
        	this.app.initNotifications();
    		this.app.activeLinkMenu(this.menu, null);
        });
    	
    	this.logout.setOnMouseClicked(clickEvent -> {
        	this.app.initLogout();
        });
    }
    
    /**
     * 
     * @return
    **/
    public int getNumberNotifications() {
		Request request = new Request(RequestType.GET_ALL_NOTIFICATIONS, this.app.getLoggedUser());
		request.setBackgroundRequest(true);
		
		ArrayList<Notification> list = ClientHelper.getListResponse(request, Notification.class);
		return list.size();
	}
    
    /**
     * 
    **/
    public void updateNumberNotifications() {
    	ScheduledService<Integer> service = new ScheduledService<Integer>() {
    		protected Task<Integer> createTask() {
        		return new Task<Integer>() {
        			protected Integer call() {
        				updateValue(getNumberNotifications());
        				return getNumberNotifications();
        			}
        		};
        	}
    	};
    	
    	service.setPeriod(Duration.seconds(3));
    	service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
            	numberNotifications.setText(t.getSource().getValue().toString());
            }
        });
    	
        service.start();
    }
    
	/**
     * Sets the reference to the application.
     * 
     * @param app the reference to the app.
    **/
    public void setApp(final App app) {
        this.app = app;
        
        this.menu.setVisible(true);
    	
        if (this.app.getLoggedUser() instanceof Employee) { 
        	Employee employee = (Employee) this.app.getLoggedUser();
        	if (employee.isAdministrator()) {
        		this.app.setVisibleElement(this.linkFees, true);
        	}
        	
        	this.app.setVisibleElement(this.linkUsers, true);
        	this.app.setVisibleElement(this.notificationsImage, false);
        	this.app.setVisibleElement(this.notificationsBlock, false);
        }
        
        this.app.activeLinkMenu(this.menu, this.linkBoats);
		this.app.initBoats();
		
		this.updateNumberNotifications();
    }
}
