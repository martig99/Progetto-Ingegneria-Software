package main.java.it.unipr.client;

import main.java.it.unipr.controller.*;
import main.java.it.unipr.message.*;
import main.java.it.unipr.model.*;

import java.io.*;
import java.text.*;
import java.util.*;

import javafx.fxml.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

/**
 * The class {@code App} defines the management of application pages using FXML files.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class App extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private Stage notificationStage;
	
	private User loggedUser;

	/**
	 * Gets the user who logged into the application.
	 * 
	 * @return the user.
	**/
	public User getLoggedUser() {
		return this.loggedUser;
	}
	
	/**
	 * Sets the user who logged into the application.
	 * 
	 * @param loggedUser the new user.
	**/
	public void setLoggedUser(final User loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
	 * {@inheritDoc}
	**/
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.setLoggedUser(null);
		
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Sailing Club");
		
		this.initFirstPage();
		this.primaryStage.show();
	}
	
	/**
	 * Initializes the first page with the logo and the name of the application.
	 * Also calls the {@link #initLogin} method to show the login section.
	**/
	public void initFirstPage() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/FirstPageLayout.fxml"));

            this.rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(this.rootLayout);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();

            this.initLogin();
        } catch (IOException e){
            e.printStackTrace();
        }
	}
	
	/**
	 * Initializes the logout.
	 * It sets the user to null and calls the login page initialization method.
	**/
	public void initLogout() {
		this.setLoggedUser(null);
		this.initFirstPage();
	}
	
	/**
	 * Initializes the login section.
	**/
	public void initLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/main/resources/LoginLayout.fxml"));
			
			VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
			
			LoginController controller = loader.getController();
			controller.setApp(this);
		} catch (IOException e){
            e.printStackTrace();
        }
	}
	
	/**
	 * Initializes the main page with the boats, races, payments, fees and users section.
	**/
	public void initMainPage() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/MainLayout.fxml"));

            this.rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(this.rootLayout);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
            
            MainController controller = loader.getController();
            controller.setApp(this);
            
            this.notificationStage = new Stage();
		} catch (IOException e){
            e.printStackTrace();
        }
	}
	
	/**
	 * Initializes the boats section.
	**/
	public void initBoats() {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/BoatsLayout.fxml"));

            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            BoatsController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the section in which to insert or update a boat.
	 * 
	 * @param boat the boat to insert or update.
	**/
	public void initUpsertBoat(final Boat boat) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/UpsertBoatLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
    		
			UpsertBoatController controller = loader.getController();
            controller.setBoat(boat);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the users section based on the type of user.
	 * 
	 * @param userType the type of user to be considered.
	**/
	public void initUsers(final UserType userType) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/UsersLayout.fxml"));

            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            UsersController controller = loader.getController();
            controller.setUserType(userType);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the section in which to insert or update an user.
	 * 
	 * @param user the user to insert or update.
	 * @param userType the type of user.
	**/
	public void initUpsertUser(final User user, final UserType userType) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/UpsertUserLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
    		
            UpsertUserController controller = loader.getController();
            controller.setUser(user);
            controller.setUserType(userType);
            
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the payments section based on the type of fee.
	 * 
	 * @param feeType the type of fee to be considered.
	**/
	public void initPayments(final FeeType feeType) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/PaymentsLayout.fxml"));

            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            PaymentsController controller = loader.getController();
            controller.setFeeType(feeType);
            controller.setApp(this);   
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the section where an employee can send a notification of payment of a particular type of fee.
	 * 
	 * @param feeType the type of fee to be considered.
	**/
	public void initNotifyPayment(final FeeType feeType) {
		try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/NotifyPaymentLayout.fxml"));

            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            NotifyPaymentController controller = loader.getController();
            controller.setFeeType(feeType);
            controller.setApp(this);   
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Initializes the notification dialog box.
	**/
	public void initNotifications() {
		try {
			this.notificationStage.close();
			
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/NotificationsLayout.fxml"));
            
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);

            NotificationsController controller = loader.getController();
            controller.setApp(this);

            this.notificationStage.setScene(scene);
            this.notificationStage.setTitle("Notifications");
            this.notificationStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Initializes the section where to pay a type of fee.
	 * 
	 * @param feeType the type of fee to be considered.
	**/
	public void initPayFee(final FeeType feeType) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/PayFeeLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            PayFeeController controller = loader.getController();
            controller.setFeeType(feeType);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Initializes the races section.
	**/
	public void initRaces() {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/RacesLayout.fxml"));

            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            RacesController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the section in which to insert or update a race.
	 * 
	 * @param race the race to insert or update.
	**/
	public void initUpsertRace(final Race race) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/UpsertRaceLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            UpsertRaceController controller = loader.getController();
            controller.setRace(race);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the registrations section at a race.
	 * 
	 * @param race the race to be considered.
	**/
	public void initRaceRegistrations(final Race race) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/RaceRegistrationsLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            RaceRegistrationsController controller = loader.getController();
            controller.setRace(race);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the section in which to insert or update a race registration.
	 * 
	 * @param race the race to be considered.
	 * @param registration the registration to insert or update.
	**/
	public void initUpsertRaceRegistration(final Race race, final RaceRegistration registration) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/UpsertRaceRegistrationLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            UpsertRaceRegistrationController controller = loader.getController();
            controller.setRace(race);
            controller.setRegistration(registration);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes fees section.
	**/
	public void initFees() {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/FeesLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            FeesController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Initializes the section in which to insert or update a fee.
	 * 
	 * @param fee the fee to insert or update.
	**/
	public void initUpdateFee(final Fee fee) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/UpdateFeeLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            UpdateFeeController controller = loader.getController();
            controller.setFee(fee);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Sets the format of the date.
	 * 
	 * @param date the date.
	 * @return a string with the formatted date.
	**/
	public String setDateFormat(final Date date) {
		String pattern = "dd/MM/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);		
	}
	
	/**
	 * Converts the string to an integer.
	 * 
	 * @param val the string.
	 * @return the integer number obtained from the conversion or -1.
	**/
	public int convertToInteger(final String val) {
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			this.showAlert(Alert.AlertType.WARNING, "Error!", null, "The entered number is incorrect.");
			return -1;
		}
	}
	
	/**
	 * Converts the string to a decimal number.
	 * 
	 * @param val the string.
	 * @return the decimal number obtained from the conversion or -1.
	**/
	public float convertToFloat(final String val) {
	    try {
	        return Float.parseFloat(val);
	    } catch (NumberFormatException e) {
	    	this.showAlert(Alert.AlertType.WARNING, "Error!", null, "The entered number is incorrect.");
	    	return -1;
	    }
	}
	
	/**
	 * Sets the format of a float number.
	 * 
	 * @param val the original float number.
	 * @return the new formatted float number.
	**/
	public float setFloatFormat(final float val) {
		DecimalFormat df = new DecimalFormat("#.00");
		df.setMaximumFractionDigits(2);
		
		DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
	    sym.setDecimalSeparator('.');
	    df.setDecimalFormatSymbols(sym);
	    
		return Float.valueOf(df.format(val));
	}

	/**
	 * Shows an alert in the application.
	 * 
	 * @param type the type of the alert.
	 * @param title the title of the alert.
	 * @param header the header of the alert.
	 * @param text the content of the alert.
	 * @return the alert.
	**/
	public Optional<ButtonType> showAlert(final Alert.AlertType type, final String title, final String header, final String text) {
		Alert alert = new Alert(type);
        alert.setTitle(title);
        
        alert.setHeaderText(header);
        alert.setContentText(text);
        
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        
        return alert.showAndWait();
	}
	
	/**
	 * Gets whether the message is successful or not.
	 * Also calls the {@link #showAlert} method to open an alert box containing the message.
	 * 
	 * @param messageType the type of message
	 * @return <code>true</code> if the message is successful.
	**/
	public boolean isSuccessfulMessage(final ResponseType messageType) {
		if (messageType != null) {
			if (messageType.isSuccessful()) {
				this.showAlert(Alert.AlertType.INFORMATION, "Excellent!", ResponseType.OK.getValue(), messageType.getValue());        
				return true;
			} else {
				this.showAlert(Alert.AlertType.WARNING, "Error", ResponseType.ERROR.getValue(), messageType.getValue());  
				return false;
			}
		}
		
		return false;
    }
	
	/**
	 * Sets the visibility of an element.
	 * 
	 * @param element the element to be considered.
	 * @param visible <code>true</code> if the element is to be visible on the page.
	**/
	public void setVisibleElement(final Node element, final boolean visible) {
	    element.setVisible(visible);
	    element.setManaged(visible);
	}
	
	/**
	 * Activates a particular graphic style at the link clicked in the menu.
	 * 
	 * @param menu the menu.
	 * @param clickedLink the link clicked in the menu.
	**/
    public void activateLinkMenu (final Parent menu, final Node clickedLink) {
    	if (menu != null) {
	    	for (Node child: menu.getChildrenUnmodifiable()) {
	    		if (child instanceof Text) {
	    			Text link = (Text) child;
	    			
	    			if (child == clickedLink) {
	    				link.setUnderline(true);
	    			} else {
	    				link.setUnderline(false);
	    			}
	    		}
	    	}
    	}
    }
	
	/**
	 * Starts the application.
	**/
	public static void main() {
		launch();
	}
}
