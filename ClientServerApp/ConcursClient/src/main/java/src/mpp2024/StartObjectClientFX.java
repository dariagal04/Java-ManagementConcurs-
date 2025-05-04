package src.mpp2024;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.OficiuController;
import src.mpp2024.domain.Participant;
import src.mpp2024.domain.PersoanaOficiu;
import src.mpp2024.objectProtocol.ConcursServicesObjectProxy;
import src.mpp2024.repo.DB.*;
import src.mpp2024.service.*;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.io.IOException;
import java.util.Properties;


public class StartObjectClientFX extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    private static Logger logger= LogManager.getLogger(StartObjectClientFX.class);

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("start application");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartObjectClientFX.class.getResourceAsStream("/concursclient/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;
        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);
        IConcursService server = new ConcursServicesObjectProxy(serverIP, serverPort);


        FXMLLoader fxmlLoader = new FXMLLoader(StartObjectClientFX.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);


        OficiuController ctr = fxmlLoader.getController();
        ctr.setServer(server);
        //ctr.setServices(categorieService, inscriereService, numeProbaService, participantiService, persoanaOficiuService);

        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.show();
    }


}
