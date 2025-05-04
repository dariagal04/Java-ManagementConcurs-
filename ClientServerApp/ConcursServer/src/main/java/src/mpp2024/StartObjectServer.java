package src.mpp2024;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.service.*;
import src.mpp2024.services.IConcursService;

import src.mpp2024.repo.Interfete.*;
import src.mpp2024.repo.DB.*;
import src.mpp2024.utils.AbstractServer;
import src.mpp2024.utils.ConcursObjectConcurrentServer;

import java.io.File;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort=55555;
    private static Logger logger= LogManager.getLogger(StartObjectServer.class);

    public static void main(String[] args) {
        Properties prop = new Properties();
        try{
            prop.load(StartObjectServer.class.getClassLoader().getResourceAsStream("server.properties"));
            logger.debug("Server properties loaded");
        }catch (IOException e){
            logger.error("Cannot find server.properties "+e.getMessage());
            logger.debug("Looking into directory {} ", (new File(".")).getAbsolutePath());
            return;
        }

        ICategorieVarstaRepo categorieVarsta = new CategorieDBRepo(prop);
        IInscriereRepo inscriere = new InscriereDBRepo(prop);
        INumeProbaRepo numeProba = new NumeProbaDBRepo(prop);
        IParticipantRepo participantRepo = new ParticipantiDBRepo(prop);
        IPersoanaOficiuRepo persoanaOficiu = new PersoanaOficiuDBRepo(prop);

        IConcursService concursServiceCategorie=new CategorieService((CategorieDBRepo) categorieVarsta);
        IConcursService concursServiceInscriere= new InscriereService((InscriereDBRepo) inscriere);
        IConcursService concursServiceNumeProba = new NumeProbaService((NumeProbaDBRepo) numeProba);
        IConcursService concursServiceParticipant = new ParticipantiService((ParticipantiDBRepo) participantRepo);
        IConcursService concursServicePersoanaOficiu = new PersoanaOficiuService((PersoanaOficiuDBRepo) persoanaOficiu);
        int serverPort=defaultPort;
        try{
            serverPort=Integer.parseInt(prop.getProperty("server.port"));
        }catch (NumberFormatException e){
            logger.error("Wrong port number "+e.getMessage());
            logger.debug("Using default port "+defaultPort);
        }
        logger.debug("Starting server on port "+serverPort);
        AbstractServer server=new ConcursObjectConcurrentServer(serverPort,concursServiceCategorie,concursServiceInscriere,concursServiceParticipant,concursServiceNumeProba,concursServicePersoanaOficiu);

        try{
            server.start();
        }catch(ServerException e){
            logger.error("Error starting the server "+e.getMessage());
        }
    }
}
