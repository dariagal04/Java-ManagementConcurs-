package src.mpp2024;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.PersoanaOficiu;
import src.mpp2024.domain.validators.ValidationException;
import src.mpp2024.service.*;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursService;

import java.io.IOException;

public class OficiuController {

//    private CategorieService categorieService;
//    private InscriereService inscriereService;
//    private NumeProbaService numeProbaService;
//    private ParticipantiService participantiService;
//    private PersoanaOficiuService persoanaOficiuService;
    private IConcursService server;
    private OficiuLoggedIn loggedInController;
    private PersoanaOficiu crtPersoanaOficiu;
    private static Logger logger= LogManager.getLogger(OficiuLoggedIn.class);

    Parent mainParent;

    public void setServer(IConcursService server){ this.server = server;}
//    public void setServices(CategorieService categorieService, InscriereService inscriereService, NumeProbaService numeProbaService, ParticipantiService participantiService, PersoanaOficiuService persoanaOficiuService) {
//        this.categorieService = categorieService;
//        this.inscriereService = inscriereService;
//        this.numeProbaService = numeProbaService;
//        this.participantiService = participantiService;
//        this.persoanaOficiuService = persoanaOficiuService;
//    }


    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    public void setMainParent(Parent mainParent) {
        this.mainParent = mainParent;
    }

    @FXML
    public void onLoginButtonClick1(ActionEvent event) {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            // Afișează un mesaj de eroare dacă nu s-a introdus un nume
            System.out.println("username and password cannot be empty.");
            return;
        }

        try {
            crtPersoanaOficiu = server.getPersoanaOficiuByUsernamePassword(username, password);
            server.login(crtPersoanaOficiu, loggedInController);
            Stage stage = new Stage();
            stage.setTitle("Oficiu: " + username);
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    //refereeController.logOut();
                    logger.debug("Closing application");
                    System.exit(0);
                }
            });

            stage.show();
            loggedInController.setUser(crtPersoanaOficiu);
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (ConcursException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();


        }
    }


//    private void openDashboard(PersoanaOficiu persoanaOficiu) {
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("persoanaOficiu-view.fxml"));
//            AnchorPane root = loader.load();
//
//
//            Stage stage = new Stage();
//            stage.setTitle("Oficiu");
//            stage.setScene(new Scene(root));
//            stage.show();
//
//            OficiuLoggedIn oficiuLoggedIn= loader.getController();
//            oficiuLoggedIn.setServer(server);
//            //oficiuLoggedIn.setService(persoanaOficiuService,inscriereService,numeProbaService,categorieService,participantiService,stage);
//            oficiuLoggedIn.setUser(persoanaOficiu);
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error loading dashboard: " + e.getMessage());
//        }
//    }



    @FXML
    public void onLoginButtonClick(ActionEvent event) {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Username și parola nu pot fi goale.");
            return;
        }

        // Create a Task to handle the login operation in the background
        Task<PersoanaOficiu> loginTask = new Task<PersoanaOficiu>() {
            @Override
            protected PersoanaOficiu call() throws Exception {
                return server.getPersoanaOficiuByUsernamePassword(username, password);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                try {
                    // This will be executed on the JavaFX Application thread after the task completes
                    crtPersoanaOficiu = get();  // This gets the result from the task
                    server.login(crtPersoanaOficiu, loggedInController);

                    Stage stage = new Stage();
                    stage.setTitle("PO profile for " + username);
                    stage.setScene(new Scene(mainParent));

                    // Handling the close request
                    stage.setOnCloseRequest(e -> {
                        logger.debug("Closing application");
                        Platform.exit();
                    });

                    stage.show();
                    loggedInController.setUser(crtPersoanaOficiu);
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    showAlert("Error during login: " + e.getMessage());
                }
            }

            @Override
            protected void failed() {
                super.failed();
                showAlert("Login failed. Please try again.");
            }
        };

        // Run the login task in the background
        new Thread(loginTask).start();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }





    public void setLoggedInController(OficiuLoggedIn loggedInController) {this.loggedInController = loggedInController;}

}