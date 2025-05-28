package src.mpp2024;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.messaging.RabbitConsumer;
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
    IConcursService server;
    private static Logger logger= LogManager.getLogger(OficiuController.class);
    PersoanaOficiu persoanaOficiu;
    Stage stage;

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
    @FXML
    private Button logoutButton;

    public void setUser(PersoanaOficiu persoanaOficiu) throws ConcursException {
        this.persoanaOficiu = persoanaOficiu;

        if (persoanaOficiu != null) {
            userNameLabel.setText(persoanaOficiu.getUsername() + " Oficiul: " + persoanaOficiu.getLocatie_oficiu());
            loadCompetitions();
        } else {
            userNameLabel.setText("No user logged in.");
        }

        RabbitConsumer.startListening(message -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notificare");
                alert.setHeaderText("Actualizare concurs");
                alert.setContentText(message);
                alert.showAndWait();
            });
        });

    }

    private void loadCompetitions() throws ConcursException {
        competitionsListView.getItems().clear();
        Map<String, Map<String, Integer>> competitions = server.getCompetitionsWithParticipants();

        if (competitions != null && !competitions.isEmpty()) {
            for (String proba : competitions.keySet()) {
                competitionsListView.getItems().add("Proba: " + proba);
                for (String categorie : competitions.get(proba).keySet()) {
                    int nrParticipanti = competitions.get(proba).get(categorie);
                    competitionsListView.getItems().add("   - " + categorie + " ani: " + nrParticipanti + " înscriși");
                }
            }
        }
    }




    @FXML
    public void onSearchButtonClick() throws ConcursException {
        String selectedProba = probaComboBox.getValue();
        String selectedCategorie = categorieComboBox.getValue();

        if (selectedProba == null || selectedCategorie == null) {
            showAlert("Te rugam sa selectezi atat proba cat si categoria de varsta.");
            return;
        }

        NumeProba proba = server.getNumeProba(selectedProba);
        CategorieVarsta categorie = server.getCategorie(Integer.parseInt(selectedCategorie));
        List<Participant> participants = server.getParticipantsByProbaAndCategorie(proba, categorie);

        participantsListView.getItems().clear();
        if (participants.isEmpty()) {
            showAlert("Nu exista participanti pentru proba si categoria selectata.");
        } else {
            for (Participant participant : participants) {
                participantsListView.getItems().add(participant.getNume() + " - Varsta: " + participant.getVarsta());
            }
        }
    }

    public void initialize() {
        probaComboBox.getItems().addAll("desen", "cautarea unei comori", "poezie");
        categorieComboBox.getItems().addAll("1", "2", "3");
        probaComboBox2.getItems().addAll("desen", "cautarea unei comori", "poezie");
    }

    public void inscriereParticipant1() throws ConcursException {
        String cnp = cnpTextField.getText();
        String nume = numeTextField.getText();
        int varsta = Integer.parseInt(varstaTextField.getText());
        CategorieVarsta categorie = server.getCategorieVarstaByAge(varsta);
        int id_categorie = categorie.getId();
        String selectedProba = probaComboBox2.getValue();
        NumeProba proba = server.getNumeProba(selectedProba);
        Participant p = new Participant(1, nume, varsta, cnp, persoanaOficiu.getId());

        if (server.getParticipantByCNP(cnp) == null) {
            boolean participantSaved = server.saveEntity(p);
            if(!participantSaved) {
                showAlert("Participantul nu a putut fi salvat!");
                return;
            }
            Inscriere i = new Inscriere(p.getId(), proba.getId(), id_categorie);
            boolean saved = server.saveEntityi(i);
            showAlert("Inscriere salvata: " + saved);
        } else {
            Participant p2 = server.getParticipantByCNP(cnp);
            Inscriere i = new Inscriere(p2.getId(), proba.getId(), id_categorie);
            boolean saved = server.saveEntityi(i);
            showAlert("Inscriere salvata: " + saved);
        }

        if(server.getParticipantByCNP(cnp) == null){
            server.saveEntity(p);
            Inscriere i = new Inscriere(p.getId(), proba.getId(), id_categorie);
            boolean saved = server.saveEntityi(i);
            showAlert("Inscriere salvata: " + saved);
        } else {
            Participant p2 = server.getParticipantByCNP(cnp);
            Inscriere i = new Inscriere(p2.getId(), proba.getId(), id_categorie);
            boolean saved = server.saveEntityi(i);
            showAlert("Inscriere salvata: " + saved);
        }
    }
    public void inscriereParticipant() throws ConcursException {
        String cnp = cnpTextField.getText();
        String nume = numeTextField.getText();
        int varsta = Integer.parseInt(varstaTextField.getText());
        CategorieVarsta categorie = server.getCategorieVarstaByAge(varsta);
        int id_categorie = categorie.getId();
        String selectedProba = probaComboBox2.getValue();
        NumeProba proba = server.getNumeProba(selectedProba);

        Participant p = server.getParticipantByCNP(cnp);

        if (p == null) {
            // Participant nou, creează-l fără id (ex: 0)
            Participant newParticipant = new Participant(1, nume, varsta, cnp, persoanaOficiu.getId());
            boolean participantSaved = server.saveEntity(newParticipant);
            if (!participantSaved) {
                showAlert("Participantul nu a putut fi salvat!");
                return;
            }
            // Actualizează id-ul după salvare (asigură-te că metoda saveEntity setează id-ul!)
            // Dacă nu, trebuie să recuperezi participantul după CNP:
            newParticipant = server.getParticipantByCNP(cnp);

            Inscriere i = new Inscriere(newParticipant.getId(), proba.getId(), id_categorie);

            boolean saved = server.saveEntityi(i);
            showAlert("Inscriere salvata: " + saved);

        } else {
            // Participant există deja
            Inscriere i = new Inscriere(p.getId(), proba.getId(), id_categorie);
            boolean saved = server.saveEntityi(i);
            showAlert("Inscriere salvata: " + saved);
        }
        loadCompetitions();


    }


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
        initialize();
    }

    @Override
    public void participantAdded(Participant participant) throws ConcursException {
        // Optional: implementare pentru actualizare în timp real
    }

    @Override
    public void oficiuLoggedIn(PersoanaOficiu persoanaOficiu) throws ConcursException {}

    @Override
    public void oficiuLoggedOut(PersoanaOficiu persoanaOficiu) throws ConcursException {
        logout();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informație");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
