package src.mpp2024;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import src.mpp2024.domain.PersoanaOficiu;
import src.mpp2024.domain.validators.ValidationException;
import src.mpp2024.service.*;
import src.mpp2024.services.IConcursService;

import java.io.IOException;

public class OficiuController {

//    private CategorieService categorieService;
//    private InscriereService inscriereService;
//    private NumeProbaService numeProbaService;
//    private ParticipantiService participantiService;
//    private PersoanaOficiuService persoanaOficiuService;
    private IConcursService server;

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


    @FXML
    public void onLoginButtonClick() throws IOException {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            // Afișează un mesaj de eroare dacă nu s-a introdus un nume
            System.out.println("username and password cannot be empty.");
            return;
        }

        try {
            PersoanaOficiu persoanaOficiu = server.login(username, password);

            if (persoanaOficiu != null) {
                System.out.println("Login successful");
                openDashboard(persoanaOficiu);

                //initModel();
                usernameTextField.clear();
                passwordTextField.clear();
            } else {
                System.out.println("Invalid username or password.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid username or password");
                alert.setHeaderText("Invalid username or password");
                alert.setContentText("Invalid username or password");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Validation Error: " + e.getMessage());}

    }

    private void openDashboard(PersoanaOficiu persoanaOficiu) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("persoanaOficiu-view.fxml"));
            AnchorPane root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Oficiu");
            stage.setScene(new Scene(root));
            stage.show();

            OficiuLoggedIn oficiuLoggedIn= loader.getController();
            oficiuLoggedIn.setServer(server);
            //oficiuLoggedIn.setService(persoanaOficiuService,inscriereService,numeProbaService,categorieService,participantiService,stage);
            oficiuLoggedIn.setUser(persoanaOficiu);



        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading dashboard: " + e.getMessage());
        }
    }

}