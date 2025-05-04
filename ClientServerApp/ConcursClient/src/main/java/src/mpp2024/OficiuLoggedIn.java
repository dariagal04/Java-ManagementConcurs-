package src.mpp2024;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.service.*;
import src.mpp2024.domain.*;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class OficiuLoggedIn implements Initializable, IConcursObserver {

//    CategorieService categorieService;
//    InscriereService inscriereService;
//    NumeProbaService numeProbaService;
//    ParticipantiService participantiService;
//    PersoanaOficiuService persoanaOficiuService;
    IConcursService server;

    private static Logger logger= LogManager.getLogger(OficiuController.class);

    PersoanaOficiu persoanaOficiu;
    Stage stage;
//    public void setService(PersoanaOficiuService persoanaOficiuService,InscriereService inscriereService,NumeProbaService numeProbaService,CategorieService categorieService,ParticipantiService participantiService,Stage stage) {
//        this.persoanaOficiuService = persoanaOficiuService;
//        this.stage = stage;
//        this.inscriereService = inscriereService;
//        this.numeProbaService = numeProbaService;
//        this.categorieService = categorieService;
//        this.participantiService = participantiService;
//    }

    public void setServer(IConcursService server){ this.server = server;}


    @FXML
    private Label userNameLabel;

    @FXML
    private ListView<String> competitionsListView;

    @FXML
    private ComboBox<String> probaComboBox;

    @FXML
    private ComboBox<String> categorieComboBox;

    @FXML
    private ListView<String> participantsListView;

    @FXML
    private Button searchButton;

    @FXML
    private TextField cnpTextField;

    @FXML
    private TextField numeTextField;

    @FXML
    private TextField varstaTextField;

    @FXML
    private ComboBox<String> probaComboBox2;

    @FXML
    private Button inscriereButton;






    public void setUser(PersoanaOficiu persoanaOficiu) {
        this.persoanaOficiu = persoanaOficiu;

        if (persoanaOficiu != null) {
            PersoanaOficiu loggedInUser = this.persoanaOficiu;

            System.out.println("Logged in user: " + loggedInUser.getUsername() + " Oficiul: "+ loggedInUser.getLocatie_oficiu());

            userNameLabel.setText(loggedInUser.getUsername() + " Oficiul: " + loggedInUser.getLocatie_oficiu());
            loadCompetitions();

        } else {
            userNameLabel.setText("No user logged in.");
            System.out.println("No user logged in.");
        }


    }



    private void loadCompetitions() {
        competitionsListView.getItems().clear();

        // Obține lista probelor și a numărului de copii înscriși
        Map<String, Map<String, Integer>> competitions = server.getCompetitionsWithParticipants();

        if (competitions == null || competitions.isEmpty()) {
            System.out.println("Nu sunt competiții disponibile.");
            return;
        }
        for (String proba : competitions.keySet()) {
            competitionsListView.getItems().add("Proba: " + proba);
            for (String categorie : competitions.get(proba).keySet()) {
                int nrParticipanti = competitions.get(proba).get(categorie);
                competitionsListView.getItems().add("   - " + categorie + " ani: " + nrParticipanti + " înscriși");
            }
        }
    }

    @FXML
    public void onSearchButtonClick() {
        String selectedProba = probaComboBox.getValue();
        NumeProba proba = server.getNumeProba(selectedProba);
        System.out.println("Selected proba: " + proba.toString());
        String selectedCategorie = categorieComboBox.getValue();
        CategorieVarsta categorie = server.getCategorie(Integer.parseInt(selectedCategorie));
        System.out.println("Selected categorie: " + categorie.toString());

        if (selectedProba == null || selectedCategorie == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atentie");
            alert.setHeaderText("Informatii lipsa");
            alert.setContentText("Te rugam sa selectezi atat proba cat si categoria de varsta.");
            alert.showAndWait();
            return;
        }

        List<Participant> participants = server.getParticipantsByProbaAndCategorie(proba, categorie);


        if (participants.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informatie");
            alert.setHeaderText("Nu au fost gasiți participanti");
            alert.setContentText("Nu exista participanti pentru proba si categoria selectata.");
            alert.showAndWait();
        } else {
            participantsListView.getItems().clear();
            for (Participant participant : participants) {
                participantsListView.getItems().add(participant.getNume() + " - Varsta: " + participant.getVarsta());
                System.out.println(participant.getNume() + " - Varsta: " + participant.getVarsta());
            }
        }
    }

    public void initialize() {
        probaComboBox.getItems().addAll("desen", "cautarea unei comori", "poezie");
        categorieComboBox.getItems().addAll("1", "2", "3");
        probaComboBox2.getItems().addAll("desen", "cautarea unei comori", "poezie");
    }



    public void inscriereParticipant(){

        String cnp = cnpTextField.getText();
        String nume = numeTextField.getText();
        int varsta = Integer.parseInt(varstaTextField.getText());
        CategorieVarsta categorie = server.getCategorieVarstaByAge(varsta);
//        String selectedCategorie = categorieComboBox.getValue();
//        CategorieVarsta categorie = categorieService.getCategorie(Integer.parseInt(selectedCategorie));
        int id_categorie = categorie.getId();
        String selectedProba = probaComboBox2.getValue();

        NumeProba proba = server.getNumeProba(selectedProba);




        Participant p = new Participant(1,nume,varsta,cnp,persoanaOficiu.getId());


        if(server.getParticipantByCNP(cnp) == null){
            server.saveEntity(p);
            Inscriere i = new Inscriere(p.getId(),proba.getId(),id_categorie);
            //inscriereService.saveEntity(i);
            boolean saved = server.saveEntityi(i);
            System.out.println("Inscriere salvata: " + saved);

            System.out.println(p.toString());
        }
        else{
            Participant p2 = server.getParticipantByCNP(cnp);
            int id_participant = p2.getId();
            Inscriere i = new Inscriere(id_participant,proba.getId(),id_categorie);
            //inscriereService.saveEntity(i);
            boolean saved = server.saveEntityi(i);
            System.out.println("Inscriere salvata: " + saved);

            System.out.println(p2.toString());
        }





    }

    @FXML
    private Button logoutButton;

    @FXML

    void logout() {
        try {
            server.logout(persoanaOficiu, this);
        } catch (Exception e) {
            logger.error("Logout error " + e);
        }
    }

    public void handleLogOut(ActionEvent event) {
        logout();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void participantAdded(Participant participant) throws ConcursException {

    }

    @Override
    public void oficiuLoggedIn(PersoanaOficiu referee) throws ConcursException {

    }

    @Override
    public void oficiuLoggedOut(PersoanaOficiu referee) throws ConcursException {

    }
}
