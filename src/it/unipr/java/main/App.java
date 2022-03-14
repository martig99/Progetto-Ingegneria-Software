package it.unipr.java.main;

import it.unipr.java.model.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	private SailingClub club;
	private MainController mainController;
	
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
	 * Gets the sailing club. 
	 * 
	 * @return the club.
	**/
	public SailingClub getClub() {
		return this.club;
	}
	
	/**
	 * Sets the sailing club.
	 * 
	 * @param club the new club.
	**/
	public void setClub(final SailingClub club) {
		this.club = club;
	}
	
	/**
	 * 
	 * @return
	**/
	public MainController getMainController() {
		return this.mainController;
	}
	
	/**
	 * 
	 * @param mainController
	**/
	public void setMainController(final MainController mainController) {
		this.mainController = mainController;
	}
	
	/**
	 * {@inheritDoc}
	**/
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.setLoggedUser(null);
		this.setClub(new SailingClub());
		this.setMainController(null);
		
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
            loader.setLocation(getClass().getResource("../../resources/FirstPageLayout.fxml"));

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
			loader.setLocation(getClass().getResource("../../resources/LoginLayout.fxml"));
			
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
            loader.setLocation(getClass().getResource("../../resources/MainLayout.fxml"));

            this.rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(this.rootLayout);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
            
            MainController controller = loader.getController();
            controller.setApp(this);
            
            this.setMainController(controller);
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
            loader.setLocation(getClass().getResource("../../resources/BoatsLayout.fxml"));

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
	public void initUpsertBoat(final Integer idBoat) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../resources/UpsertBoatLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
    		
			UpsertBoatController controller = loader.getController();
            controller.setIdBoat(idBoat);
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
            loader.setLocation(getClass().getResource("../../resources/UsersLayout.fxml"));

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
	public void initUpsertUser(final Integer idUser, final UserType userType) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../resources/UpsertUserLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
    		
            UpsertUserController controller = loader.getController();
            controller.setIdUser(idUser);
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
            loader.setLocation(getClass().getResource("../../resources/PaymentsLayout.fxml"));

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
	 * Initializes the dialog panel to pay a fee.
	**/
	public void initPayFee(final FeeType type) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../resources/PayFeeLayout.fxml"));
            
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
            loader.setLocation(getClass().getResource("../../resources/RacesLayout.fxml"));

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
	public void initUpsertRace(final Integer idRace) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../resources/UpsertRaceLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            UpsertRaceController controller = loader.getController();
            controller.setIdRace(idRace);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Initializes the dialog panel to register a boat to a race.
	**/
	public void initUpsertRaceRegistration(final int idRace, final Integer idRegistration) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../resources/UpsertRaceRegistrationLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            UpsertRaceRegistrationController controller = loader.getController();
            controller.setIdRace(idRace);
            controller.setIdRegistration(idRegistration);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 
	**/
	public void initRaceRegistrations(final int idRace) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../resources/RaceRegistrationsLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            RaceRegistrationsController controller = loader.getController();
            controller.setIdRace(idRace);
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
            loader.setLocation(getClass().getResource("../../resources/FeesLayout.fxml"));
            
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
	public void initUpdateFee(final int idFee) {
        try {
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../resources/UpdateFeeLayout.fxml"));
            
            VBox overview = (VBox) loader.load();
			this.rootLayout.setCenter(overview);
            
            UpdateFeeController controller = loader.getController();
            controller.setIdFee(idFee);
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Checks if the email matches the correct format.
	 * 
	 * @param email the email to check. 
	 * @return <code>true</code> if email is correct.
	**/
	public boolean emailValidation(final String email) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		
		return Pattern.compile(regexPattern).matcher(email).matches();
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
	 * 
	 * @param date
	 * @return
	**/
	public Date getZeroTimeDate(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
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
	public double convertToDouble(final String val) {
	    try {
	        return Double.parseDouble(val);
	    } catch (NumberFormatException e) {
	    	this.showAlert(Alert.AlertType.WARNING, "Error!", null, "The entered number is incorrect.");
	    	return -1;
	    }
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
     * 
    **/
    public void toggleLinkMenu (final Parent parent, final boolean val) {
    	if (parent != null) {
	    	for (Node child: parent.getChildrenUnmodifiable()) {
	    		if (child instanceof Text) {
	    			Text link = (Text) child;
	    			link.setDisable(val);
	    		}
	    	}
    	}
    }
    
	/**
	 * 
	**/
	public void setVisibleElement(final Node element, final boolean visible) {
	    element.setVisible(visible);
	    element.setManaged(visible);
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
	 * Starts the application.
	 *
	 * @param args the method does not requires arguments.
	**/
	public static void main(String[] args) {
		launch(args);
	}

}
