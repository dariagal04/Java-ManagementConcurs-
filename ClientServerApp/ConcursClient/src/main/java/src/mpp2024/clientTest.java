package src.mpp2024;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.Participant;
import src.mpp2024.domain.PersoanaOficiu;
import src.mpp2024.dto.PersoanaOficiuDTO;
import src.mpp2024.objectProtocol.ConcursServicesObjectProxy;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class clientTest {

    private static final Logger logger = LogManager.getLogger(clientTest.class);

    public static void main(String[] args) throws ConcursException {
        // Configurare server
        String serverIp = "localhost"; // IP-ul serverului
        int serverPort = 55556; // Portul serverului

        // Crearea unui client pentru serverul de MotoServices
        IConcursService server = new ConcursServicesObjectProxy(serverIp, serverPort);

        // Crearea unui utilizator de test pentru login
        PersoanaOficiu persoanaOficiu= new PersoanaOficiu(1, "marinela", "1234","centru");
        PersoanaOficiu persoanaOficiu1 = server.getPersoanaOficiuByUsernamePassword("marinela","1234");
        System.out.println(persoanaOficiu1);


        try {
            // Încercare login
            server.login(persoanaOficiu, new IConcursObserver() {




                @Override
                public void participantAdded(Participant participant) {
                    // Aici, implementăm doar un mesaj de log pentru test
                    logger.info("Participant added: " + participant);
                }

                @Override
                public void oficiuLoggedIn(PersoanaOficiu persoanaOficiu) throws ConcursException {
                    logger.info("User logged in: " + persoanaOficiu);

                }

                @Override
                public void oficiuLoggedOut(PersoanaOficiu persoanaOficiu) throws ConcursException {

                    logger.info("User logged out: " + persoanaOficiu);

                }
            });

            logger.info("Login successful!- "+ persoanaOficiu.toString());

            // Test: cerem toți participanții
            logger.info("Fetching all participants...");
            Map<String, Map<String, Integer>> participants = server.getCompetitionsWithParticipants();

            //logger.info("Participants: " + participants);

            // Test logout
            server.logout(persoanaOficiu, null);
            logger.info("Logout successful!");

        } catch (Exception e) {
            logger.error("MotoException: " + e.getMessage());

        }
    }
}