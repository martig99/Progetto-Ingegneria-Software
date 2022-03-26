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
 * The class {@code App} defines the creation of application pages using FXML files.
 * 
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/
public class App extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
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
	 * Initializes the first page with the login and sign up section.
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
	 * Initializes the login page.
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
	 * Initializes the logout.
	 * It sets the user to null and calls the login page initialization method.
	**/
	public void initLogout() {
		this.setLoggedUser(null);
		this.initFirstPage();
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
		} catch (IOException e){
            e.printStackTrace();
        }
	}
	
	/**
	 * Initializes the boats page.
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
	 * Initializes the dialog panel to update or insert a boat.
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
	 * Initializes the users page.
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
	 * Initializes the page to create a new user.
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
	 * Initializes the payments page.
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
	 * 
	 * @param feeType
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
	 * Initializes the dialog panel to pay a fee.
	**/
	public void initPayFee(final FeeType type) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/PayFeeLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            PayFeeController controller = loader.getController();
            controller.setFeeType(type);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Initializes the races page.
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
	 * Initializes the dialog panel to add or update a race.
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
	 * Initializes the dialog panel to register a boat to a race.
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
	 * 
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
	 * Initializes the dialog panel to show fees.
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
	 * Initializes the dialog panel to update a fee.
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
	 * 
	**/
	public void initNotifications() {
		try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/resources/NotificationsLayout.fxml"));
            
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);

            NotificationsController controller = loader.getController();
            controller.setApp(this);

            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);
            dialogStage.setTitle("Notifications");
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 
	 * @param date
	 * @return
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
	 * 
	 * @param val
	 * @return
	**/
	public float setFloatFormat(final float val) {
		DecimalFormat df = new DecimalFormat();
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
	 * @param text the content of the alert.
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
	 * 
	 * @param obj
	**/
	public boolean getMessage(final ResponseType responseType) {
		if (responseType != null) {
			if (responseType.isSuccess()) {
				this.showAlert(Alert.AlertType.INFORMATION, "Excellent!", ResponseType.OK.getValue(), responseType.getValue());        
				return true;
			} else {
				this.showAlert(Alert.AlertType.WARNING, "Error", ResponseType.ERROR.getValue(), responseType.getValue());  
				return false;
			}
		}
		
		return false;
    }
	
	/**
	 * 
	**/
	public void setVisibleElement(final Node element, final boolean visible) {
	    element.setVisible(visible);
	    element.setManaged(visible);
	}
	
	/**
     * 
     * @param clickedLink the link of the clicked menu.
    **/
    public void activeLinkMenu (final Parent parent, final Node clickedLink) {
    	if (parent != null) {
	    	for (Node child: parent.getChildrenUnmodifiable()) {
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
